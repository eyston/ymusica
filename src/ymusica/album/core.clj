(ns ymusica.album.core
  (:require [ymusica.lastfm.api :as api]
            [ymusica.settings :as settings]
            [clojure.java.jdbc :as j]
            [clojure.java.jdbc.sql :as s]
            [cheshire.core :as json]))

(defn- transform-album-images [images]
    (into {} (map (fn [image] [(:size image) (:#text image)]) images)))

(defn- transform-album [album]
  (let [album (select-keys album [:name :artist :image])
        album (update-in album [:image] transform-album-images)
        album (clojure.set/rename-keys album { :image :images })]
    album))

(defn- search-api [term]
  (let [albums (api/get {:method "album.search" :album term})
        albums (get-in albums [:results :albummatches :album] [])
        albums (if (vector? albums) albums (vector albums))
        albums (map transform-album albums)]
    {:albums albums}))

(defn- cache-lookup [term]
  (if-let [row (j/query settings/db
                        (s/select :data :album_search_cache (s/where {:term term :version settings/cache-version})))]
    (-> row
        (first)
        (:data)
        (json/parse-string true))))

(defn- cache-miss [term data]
  (let [row {:term term :data (json/generate-string data) :version settings/cache-version}]
    (j/insert! settings/db :album_search_cache row)
    data))

(defn search [term]
  (if-let [albums (cache-lookup term)]
    albums
    (cache-miss term (search-api term))))
