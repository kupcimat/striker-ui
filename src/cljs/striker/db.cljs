(ns striker.db
  (:require
    [striker.fixtures :as fixtures]))

(def default-db
  {:search-results fixtures/search-results
   :hotel          fixtures/hotel})
