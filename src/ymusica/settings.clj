(ns ymusica.settings)

(def db (System/getenv "DATABASE_URL"))
(def lastfm-api-key (System/getenv "LASTFM_API_KEY"))
(def cache-version 1)