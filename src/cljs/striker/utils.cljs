(ns striker.utils
  (:require
    [re-frame.core :as re-frame]))

(def >evt re-frame/dispatch)
(def <sub (comp deref re-frame/subscribe))

(defn unwrap [input keyword-set]
  (cond
    (map? input) (reduce-kv (fn [map key value]
                              (if (contains? keyword-set key)
                                  (merge map (unwrap value keyword-set))
                                  (assoc map key (unwrap value keyword-set))))
                            {} input)
    (vector? input) (reduce (fn [vector value]
                              (conj vector (unwrap value keyword-set)))
                            [] input)
    :else input))
