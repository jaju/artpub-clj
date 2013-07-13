(ns artpub.main
  (:require [ajax.core :refer [GET POST]] 
            [domina :refer [value by-id destroy-children! append!]]
            [domina.events :refer [listen!]]
            [dommy.core :as dommy]
            [dommy.attrs :as attrs]
            [dommy.template :as template])
  (:use-macros
   [dommy.macros :only [sel sel1 node]]))

(defn render-message [{:keys [message user]}]
  [:li [:p {:id user} message " - " user]])

(defn render-messages [messages]
  (let [messages-div (by-id "messages")]
    (destroy-children! messages-div)
    (->> messages
         (map render-message)         
         (into [:ul])       
         template/node     
         (append! messages-div))))

(defn add-message [_]
  (POST "/add-message" 
        {:params {:message (value (by-id "message"))
                  :user    (value (by-id "user"))}
         :handler render-messages}))

(defn get-sections []
  (sel [:#content :section]))

(defn article-nav-node []  (sel1 [:#left-sidenav :.sidenav]))

(defn create-section-nav-elem [section]
  (let [header (sel1 section [:header])
        text (dommy/text header)
        href (str "#" (attrs/attr section :id))]
    (node [:li [:a {:href href} text]])))

(defn populate-article-navigator []
  (doseq [section (get-sections)]
    (dommy/append! (article-nav-node) (create-section-nav-elem section))))

(defn ^:export init []
  (GET "/messages" {:handler render-messages})
  (listen! (by-id "send") :click add-message)
  (populate-article-navigator))

(set! (.-onload js/window) init)
