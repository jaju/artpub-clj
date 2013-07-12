(ns version-checker
  (:require [clj-http.client :as client]))
 
(def projects
  ["https://raw.github.com/noir-clojure/lib-noir/master/project.clj"
   "https://raw.github.com/weavejester/compojure/master/project.clj"
   "https://raw.github.com/weavejester/hiccup/master/project.clj"
   "https://raw.github.com/weavejester/lein-ring/master/project.clj"
   "https://raw.github.com/ptaoussanis/timbre/master/project.clj"
   "https://raw.github.com/ptaoussanis/tower/master/project.clj"
   "https://raw.github.com/yogthos/markdown-clj/master/project.clj"
   "https://raw.github.com/weavejester/lein-ring/master/project.clj"
   "https://raw.github.com/weavejester/ring-mock/master/project.clj"
   "https://raw.github.com/mmcgrana/ring/master/ring-devel/project.clj"
   "https://raw.github.com/korma/Korma/master/project.clj"])

(defn get-project-version [url]
  (let [resp (client/get url)]
    (if (= "text/plain; charset=utf-8" (get (:headers resp) "content-type"))
      (binding [*read-eval* false]
        (->> resp :body read-string rest (take 2)))
      (println "error fetching project file for" url))))
 
(defn versions []
  (for [url projects]
    (get-project-version url)))

(defn print-latest-versions []
  (doseq [[id version] (versions)]
  (println id version)))
