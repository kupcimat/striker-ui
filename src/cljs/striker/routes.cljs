(ns striker.routes
  (:require
    [re-frame.core :as re-frame]
    [reitit.frontend :as reitit-fe]
    [reitit.frontend.easy :as reitit-fe-easy]
    [striker.events :as events]))

(def routes
  [["/"          {:name ::root-path}]
   ["/hotel/:id" {:name ::hotel-path}]])

(def router
  (reitit-fe/router routes))

(defn on-navigate [match]
  (when match
    (re-frame/dispatch [::events/set-active-panel (get-in match [:data :name])])))

(defn init-routes []
  (reitit-fe-easy/start!
    router
    on-navigate
    {:use-fragment true}))

(defn route->path
  ([name]        (route->path name nil))
  ([name params] (reitit-fe-easy/href name params)))
