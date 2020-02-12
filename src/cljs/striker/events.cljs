(ns striker.events
  (:require
    [day8.re-frame.http-fx]
    [ajax.core :as ajax]
    [re-frame.core :as re-frame]
    [striker.db :as db]
    [striker.utils :as utils]))

;; Event handlers

(re-frame/reg-event-db
  ::initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/reg-event-db
  ::set-active-panel
  (fn [db [_ active-panel]]
    (assoc db :active-panel active-panel)))

(re-frame/reg-event-fx
  ::filter-search-results
  (fn [_world [_ query]]
    {:http-xhrio {:method          :get
                  :uri             "/api/agoda/search"
                  :params          {:query query}
                  :timeout         8000
                  :response-format (ajax/json-response-format {:keywords? true})
                  :on-success      [::filter-search-results-ok]
                  :on-failure      [::http-result-error "Search error"]}}))

(re-frame/reg-event-db
  ::filter-search-results-ok
  (fn [db [_ result]]
    (assoc db :search-results (-> result (utils/unwrap #{:searchResult :item}) (:items)))))

(re-frame/reg-event-fx
  ::http-result-error
  (fn [_world [_ message error]]
    {:display-message (str message " (" (:status-text error) ")")}))

;; Effect handlers

(re-frame/reg-fx
  :display-message
  (fn [message]
    (.toast js/M (clj->js {:html message}))))
