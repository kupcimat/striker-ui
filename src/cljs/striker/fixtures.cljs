(ns striker.fixtures)

(defn parse-json [json-string]
  (js->clj (.parse js/JSON json-string) :keywordize-keys true))

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

(def search-results-json
  "{\"searchResult\":{\"items\":[{\"item\":{\"category\":\"Property\",\"id\":1622759,\"location\":\"Singapore,Singapore\",\"name\":\"ParkHotelFarrerPark\"}},{\"item\":{\"category\":\"Landmark\",\"id\":26127,\"location\":\"Singapore\",\"name\":\"FarrerParkMRTStation\"}},{\"item\":{\"category\":\"Property\",\"id\":1163141,\"location\":\"Singapore,Singapore\",\"name\":\"ZENRoomsFarrerPark\"}},{\"item\":{\"category\":\"Property\",\"id\":7286594,\"location\":\"Singapore,Singapore\",\"name\":\"Residences@FarrerPark\"}},{\"item\":{\"category\":\"Property\",\"id\":2347682,\"location\":\"Singapore,Singapore\",\"name\":\"ZENHostelFarrerPark\"}}]}}")

(def hotel-json
  "{\"hotel\":{\"id\":10408,\"name\":\"Hotel Istana Kuala Lumpur City Centre\",\"rooms\":[{\"room\":{\"id\":3135829,\"name\":\"Deluxe \",\"variants\":[{\"variant\":{\"id\":\"46e9e685-a64f-1d85-e059-527943cbe89f\",\"pricePerNight\":1628664.0,\"priceTotal\":6514656.0,\"currency\":\"VND\",\"payLater\":false,\"payAtHotel\":false,\"freeCancellation\":false,\"breakfastIncluded\":false}},{\"variant\":{\"id\":\"21833d69-09bf-e7d4-03d8-e68dd6edade4\",\"pricePerNight\":1952913.0,\"priceTotal\":7811652.0,\"currency\":\"VND\",\"payLater\":false,\"payAtHotel\":false,\"freeCancellation\":false,\"breakfastIncluded\":true}}]}},{\"room\":{\"id\":12476528,\"name\":\"Deluxe Twin Towers View\",\"variants\":[{\"variant\":{\"id\":\"b3f59b8b-388a-e460-34cb-4c52ea0fe65c\",\"pricePerNight\":1787184.0,\"priceTotal\":7148736.0,\"currency\":\"VND\",\"payLater\":false,\"payAtHotel\":false,\"freeCancellation\":false,\"breakfastIncluded\":false}},{\"variant\":{\"id\":\"fb696d14-5bbc-8193-00e7-bb86826ee94b\",\"pricePerNight\":2098838.0,\"priceTotal\":8395352.0,\"currency\":\"VND\",\"payLater\":false,\"payAtHotel\":false,\"freeCancellation\":false,\"breakfastIncluded\":true}}]}},{\"room\":{\"id\":3135825,\"name\":\"Club Suite\",\"variants\":[{\"variant\":{\"id\":\"ebe7bf8c-4a33-7584-0d8a-90ca65206fc3\",\"pricePerNight\":3239457.0,\"priceTotal\":1.2957828E7,\"currency\":\"VND\",\"payLater\":false,\"payAtHotel\":false,\"freeCancellation\":false,\"breakfastIncluded\":true}}]}}]}}")

(def search-results
  (-> search-results-json
    (parse-json)
    (unwrap #{:searchResult :item})
    (:items)))

(def hotel
  (-> hotel-json
    (parse-json)
    (unwrap #{:hotel :room :variant})))
