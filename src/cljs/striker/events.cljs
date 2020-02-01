(ns striker.events
  (:require
    [clojure.string :as string]
    [re-frame.core :as re-frame]
    [striker.db :as db]))

(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/reg-event-db
  ::set-active-panel
  (fn [db [_ active-panel]]
    (assoc db :active-panel active-panel)))

(re-frame/reg-event-db
  ::filter-search-results
  (fn [db [_ query]]
    (assoc db :search-results (filter #(string/includes? (string/lower-case (:name %)) (string/lower-case query))
                                      (:search-results db/default-db)))))
