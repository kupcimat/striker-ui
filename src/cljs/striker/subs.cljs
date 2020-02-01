(ns striker.subs
  (:require
    [re-frame.core :as re-frame]))

(re-frame/reg-sub
  ::active-panel
  (fn [db _]
    (:active-panel db)))

(re-frame/reg-sub
  ::search-results
  (fn [db _]
    (:search-results db)))

(re-frame/reg-sub
  ::hotel
  (fn [db _]
    (:hotel db)))
