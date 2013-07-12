(defproject
  artpub
  "0.1.0-SNAPSHOT"

  :dependencies
  [[org.clojure/clojure "1.5.1"]
   [lib-noir "0.6.6"]
   [compojure "1.1.5"]
   [ring-server "0.2.8"]
   [clabango "0.5"]
   [com.taoensso/timbre "2.1.2"]
   [com.postspectacular/rotor "0.1.0"]
   [com.taoensso/tower "1.7.1"]
   [markdown-clj "0.9.28"]
   [org.clojure/clojurescript "0.0-1806"]
   [domina "1.0.1"]
   [prismatic/dommy "0.1.1"]
   [cljs-ajax "0.1.4"]
   [com.h2database/h2 "1.3.172"]
   [korma "0.3.0-RC5"]
   [log4j
    "1.2.17"
    :exclusions
    [javax.mail/mail
     javax.jms/jms
     com.sun.jdmk/jmxtools
     com.sun.jmx/jmxri]]]

  :cljsbuild
  {:builds
   [{:source-paths ["src-cljs"],
     :compiler
     {:pretty-print false,
      :output-to "resources/public/js/site.js",
      :optimizations :advanced}}]}

  :ring
  {:handler artpub.handler/war-handler,
   :init artpub.handler/init,
   :destroy artpub.handler/destroy}

  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}},
   :dev
   {:dependencies [[ring-mock "0.1.5"] [ring/ring-devel "1.1.8"]]}}

  :url "http://msync.org/"

  :plugins [[lein-ring "0.8.5"] [lein-cljsbuild "0.3.2"]]

  :description "A Simple Articles Publisher"

  :min-lein-version "2.0.0")
