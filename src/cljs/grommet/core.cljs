(ns grommet.core
  (:require [cljsjs.grommet]
            [clojure.string :as str]
            [reagent.core :as reagent :refer [atom]]
            [reagent.session :as session]
            [secretary.core :as secretary :include-macros true]
            [accountant.core :as accountant]))

(def ^:private grommet js/Grommet)

(defn $ [kw]
  (let [c-keys (str/split (name kw) ".")]
    (apply goog.object/getValueByKeys grommet c-keys)))

;; -------------------------
;; Views

(defn home-page []
  [:> ($ :App)
   [:div [:h2 "Welcome to grommet"]
    [:div [:a {:href "/about"} "go to about page"]]]])

(defn about-page []
  [:div [:h2 "About grommet"]
   [:div [:a {:href "/"} "go to the home page"]]])

(defn current-page []
  [:div [(session/get :current-page)]])

;; -------------------------
;; Routes

(secretary/defroute "/" []
  (session/put! :current-page #'home-page))

(secretary/defroute "/about" []
  (session/put! :current-page #'about-page))

;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
