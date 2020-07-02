(ns sublist-bench
  (:require [criterium.core :as cc]
            sublist))

(defn- sublist?*
  ([list1 list2]
  (cond
    (= list1 (take (count list1) list2)) true
    (empty? list2) false
    :else (recur list1 (rest list2)))))

(defn classify* [list1 list2]
  (let [lt (sublist?* list1 list2)
        gt (sublist?* list2 list1)]
    (cond
      (and lt gt) :equal
      lt :sublist
      gt :superlist
      :else :unequal)))

(def list1-cases
  [[]
   []
   [1 2 3]
   [1 2 3]
   [1 2 3]
   [1 2 5]
   [1 1 2]
   [0 1 2]
   [2 3 4]
   [3 4 5]
   [0 1 2 3 4 5]
   [0 1 2 3 4 5]
   [0 1 2 3 4 5]
   [1 3]
   [1 2 3]
   [1 2 3]
   [1 0 1]])

(def list2-cases
  [[]
   [1 2 3]
   []
   [1 2 3]
   [2 3 4]
   [0 1 2 3 1 2 5 6]
   [0 1 1 1 2 1 2]
   [0 1 2 3 4 5]
   [0 1 2 3 4 5]
   [0 1 2 3 4 5]
   [0 1 2]
   [2 3]
   [3 4 5]
   [1 2 3]
   [1 3]
   [3 2 1]
   [10 1]])

(defn bench [] (cc/quick-bench (dorun (map sublist/classify list1-cases list2-cases))))
(defn bench* [] (cc/quick-bench (dorun (map classify* list1-cases list2-cases))))
