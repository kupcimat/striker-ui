(ns striker.util
  (:require
    [clojure.java.io :as io]
    [clojure.data.json :as json]))

(defn load-pom-properties [filename]
  (with-open [pom-properties-reader (io/reader (io/resource filename))]
    (let [properties (doto (java.util.Properties.) (.load pom-properties-reader))]
      (into {} properties))))

(defn write-json-properties [properties filename]
  (with-open [json-properties-writer (io/writer (io/file filename))]
    (json/write properties json-properties-writer)))

(defn transform-properties [properties]
  (assoc {} :build properties))

(defn build-info [group artifact filename]
  (-> (str "META-INF/maven/" group "/" artifact "/pom.properties")
    (load-pom-properties)
    (transform-properties)
    (write-json-properties filename)))
