(ns ymusica.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [ring.util.response :as response]
            [ring.middleware.params :as params]
            [ring.middleware.resource :as resource]
            [ring.middleware.file-info :as file-info]
            [ring.middleware.json :as json]
            [ymusica.album.core :as album]
            [ymusica.artist.core :as artist]))

(defroutes api
  (GET "/album" [q] {:body (album/search q)})
  (GET "/artist" [q] {:body (artist/search q)}))

(defroutes main
  (context "/api" [] (json/wrap-json-response api))
  (GET "/" [] (response/resource-response "index.html" {:root ""}))
  (route/not-found (response/resource-response "404.html" {:root ""})))

(def app
  (-> main
      (resource/wrap-resource "")
      (file-info/wrap-file-info)
      (params/wrap-params)))
