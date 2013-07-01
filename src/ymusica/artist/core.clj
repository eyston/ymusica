(ns ymusica.artist.core
  (:require [ymusica.lastfm.api :as api]
            [ymusica.settings :as settings]
            [clojure.java.jdbc :as j]
            [clojure.java.jdbc.sql :as s]
            [cheshire.core :as json]))

(defn- transform-images [images]
    (into {} (map (fn [image] [(:size image) (:#text image)]) images)))

(defn- transform-artist [artist]
  (let [artist (select-keys artist [:name :image])
        artist (update-in artist [:image] transform-images)
        artist (clojure.set/rename-keys artist {:image :images})]
    artist))

(defn- search-api [term]
  (let [artists (api/get {:method "artist.search" :artist term})
        artists (get-in artists [:results :artistmatches :artist] [])
        artists (if (vector? artists) artists (vector artists))
        artists (map transform-artist artists)]
    {:artists artists}))

(defn- cache-lookup [term]
  (if-let [row (j/query settings/db
                        (s/select :data :artist_search_cache (s/where {:term term :version settings/cache-version})))]
    (-> row
        (first)
        (:data)
        (json/parse-string true))))

(defn- cache-miss [term data]
  (let [row {:term term :data (json/generate-string data) :version settings/cache-version}]
    (j/insert! settings/db :artist_search_cache row)
    data))

(defn search [term]
  (if-let [artists (cache-lookup term)]
    artists
    (cache-miss term (search-api term))))