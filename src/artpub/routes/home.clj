(ns artpub.routes.home
  (:use compojure.core)
  (:require [artpub.views.layout :as layout]
            [artpub.util :as util]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
   "home.html" {:content (util/md->html "/md/docs.md")}))

(defn article-html-and-then-md [name]
  (if-let [f (io/resource (str "public/articles-html/" name ".html"))]
    (slurp f)
    (util/md->html (str "/md/" name ".md"))))

(defn article [name]
  (layout/render
   "home.html" {:content (article-html-and-then-md name)}))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/articles/:name" [name] (article name))
  (GET "/about" [] (about-page)))
