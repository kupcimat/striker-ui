(ns striker.db
  (:require
    [striker.fixtures :as fixtures]))

(def default-db
  {:active-panel   nil
   :search-results fixtures/search-results
   :hotel          fixtures/hotel})
