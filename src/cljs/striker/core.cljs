(ns striker.core
  (:require
    [reagent.dom :as reagent]
    [re-frame.core :as re-frame]
    [striker.events :as events]
    [striker.routes :as routes]
    [striker.views :as views]
    [striker.config :as config]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (routes/init-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
