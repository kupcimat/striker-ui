(ns striker.routes
  (:require
    [re-frame.core :as re-frame]
    [reitit.frontend :as reitit-fe]
    [reitit.frontend.easy :as reitit-fe-easy]
    [striker.events :as events]))

(def routes
  [["/"          {:name ::search-panel}]
   ["/hotel/:id" {:name ::hotel-panel}]])

(def router
  (reitit-fe/router routes))

(defn on-navigate [match]
  (let [route (if match (get-in match [:data :name]) ::not-found-panel)]
    (re-frame/dispatch [::events/set-active-panel route])))

(defn init-routes []
  (reitit-fe-easy/start!
    router
    on-navigate
    {:use-fragment true}))

(defn route->path
  ([name]        (route->path name nil))
  ([name params] (reitit-fe-easy/href name params)))
