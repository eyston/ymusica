(ns ymusica.schema.core
  (:require [ymusica.settings :as settings]
            [clojure.java.jdbc :as j]
            [clojure.java.jdbc.sql :as s]))

(defn create-album-search-cache []
  (j/db-do-commands settings/db false
                    "DROP TABLE IF EXISTS album_search_cache;"
                    (str "CREATE TABLE album_search_cache ("
                         "id SERIAL, "
                         "term varchar(255) NOT NULL, "
                         "version int, "
                         "data text, "
                         "created_at timestamp default current_timestamp"
                         ");")))


(defn create-artist-search-cache []
  (j/db-do-commands settings/db false
                    "DROP TABLE IF EXISTS artist_search_cache;"
                    (str "CREATE TABLE artist_search_cache ("
                         "id SERIAL, "
                         "term varchar(255) NOT NULL, "
                         "version int, "
                         "data text, "
                         "created_at timestamp default current_timestamp"
                         ");")))

(defn- main [args]
  (do
    (create-album-search-cache)
    (create-artist-search-cache)))