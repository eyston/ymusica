(ns ymusica.lastfm.api
  (:require [ymusica.settings :as settings]
            [clj-http.client :as client]
            [clojure.set])
  (:refer-clojure :exclude [get]))

(def api-key settings/lastfm-api-key)
(def base-api-url "http://ws.audioscrobbler.com/2.0/")

(defn api-url [args]
  (let [args (merge {:api_key api-key :format "json"} args)
        arg-strings (for [[k v] args] (str (name k) "=" v))
        args-string (clojure.string/join "&" arg-strings)]
    (str base-api-url "?" args-string)))

(defn get [args]
  (let [url (api-url args)]
    (:body (client/get url {:as :json}))))