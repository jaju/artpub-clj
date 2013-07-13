(ns artpub.views.layout
  (:use noir.request)
  (:require [clabango.parser :as parser]
            [noir.session :as session]
            [artpub.models.db :as db]))

(def template-path "artpub/views/templates/")

(defn user-display-name [id]
  (let [user (db/get-user id)]
    (if-let [display (:first_name user)]
      display
      (str "(" id ")"))))

(defn render [template & [params]]
  (parser/render-file (str template-path template)
                      (assoc (or params {})
                        :context (:context *request*)
                        :user-id (session/get :user-id)
                        :user-name (user-display-name (session/get :user-id)))))
