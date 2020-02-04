(ns striker.views
  (:require
    [re-frame.core :as re-frame]
    [striker.events :as events]
    [striker.routes :as routes]
    [striker.subs :as subs]))

(defn icon [name]
  [:i.material-icons name])

(defn search-component []
  [:nav>div.nav-wrapper
   [:form>div.input-field
    [:input#search {:type "search"
                    :on-change #(re-frame/dispatch [::events/filter-search-results (-> % .-target .-value)])}]
    [:label.label-icon {:for "search"}
     [icon "search"]]
    [icon "close"]]])

(defn search-panel []
  (let [results (re-frame/subscribe [::subs/search-results])]
    [:div.collection
      (for [item @results]
        [:a.collection-item {:key (:id item) :href "#"}
         (:name item)
         [:span.secondary-content
          [icon (case (:category item)
                  "Property" "home"
                  "Landmark" "location_on"
                  "location_on")]]])]))

(defn show-panel [panel-name]
  (case panel-name
    ::routes/search-panel [search-panel]
    ::routes/hotel-panel [:div>p "todo hotel"]
    [:div]))

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div.row
     [:div.col.s12
      [:div.section
       [search-component]]
      [:div.section
       [show-panel @active-panel]]]]))
