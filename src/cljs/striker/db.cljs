(ns striker.db
  (:require
    [cljs.spec.alpha :as s]))

(def default-db
  {:active-panel   nil
   :search-results []
   :hotel          {}})
