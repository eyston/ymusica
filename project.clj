(defproject ymusica "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :plugins [[lein-ring "0.8.3"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-core "1.1.6"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [ring/ring-json "0.2.0"]
                 [cheshire "5.0.2"]
                 [compojure "1.1.5"]
                 [clj-http "0.7.1"]
                 [org.clojure/java.jdbc "0.3.0-alpha1"]
                 [postgresql "9.1-901.jdbc4"]]
  :main ymusica.core
  :ring { :handler ymusica.core/app }
  :profiles {:dev {:resource-paths ["client/dist"]}})
