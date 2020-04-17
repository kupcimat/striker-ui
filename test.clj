(defn remove-odd [[head head2 & rest]]
  (if (or (nil? head) (nil? head2))
      (list)
      (conj (remove-odd rest) head2)))

(remove-odd '(1 2 3 4 5))

(defn reverse-list [[head & rest]]
  (if (nil? head)
    (vector)
    (conj (reverse-list rest) head)))

(reverse-list '(1 2 3))

(defn sum-odd [[head & rest]]
  (if (nil? head)
      0
      (if (= 0 (mod head 2))
          (sum-odd rest)
          (+ head (sum-odd rest)))))

(sum-odd '(3 2 4 6 5 7 8 0 1))

(defn count-list [[head & rest]]
  (if (nil? head)
      0
      (+ 1 (count-list rest))))

(count-list '(1 2 3 4 5))

(defn update-list [[head & rest]]
  (if (nil? head)
      (list)
      (conj (update-list rest) (max head (- head)))))

(update-list '(0 1 -2 3 -4 5))

; ---------------------------

(defn pow [x n]
  (if (= n 0)
      1
      (* x (pow x (- n 1)))))

(defn fact [x]
  (if (= x 0)
      1
      (* x (fact (- x 1)))))

(defn ex [x n]
  (if (= n 0)
      1
      (+ (/ (pow x n) (fact n)) (ex x (- n 1)))))

(ex 20 9)

(def x 20)
(reduce + (map (fn [n] (/ (reduce * (repeat n x))
                          (reduce * (range 1 (inc n)))))
               (range 10)))

; ---------------------------

(defn read-int []
  (-> (read-line)
    (clojure.string/trim)
    (Integer/parseInt)))

(defn read-int-list []
  (as-> (read-line) value
    (clojure.string/trim value)
    (clojure.string/split value #" ")
    (map #(Integer/parseInt %) value)
    (into [] value)))

(defn read-int-lines
  ([count]     (read-int-lines count []))
  ([count acc] (if (<= count 0)
                   acc
                   (read-int-lines (dec count) (conj acc (read-int-list))))))

(defn fx [params x]
  (reduce + (map (fn [[a b]] (* a (Math/pow x b))) params)))

(defn f-area [f-params]
  (partial fx f-params))

(defn f-volume [f-params]
  (comp (fn [r] (* Math/PI r r)) (partial fx f-params)))

(defn integral [[l r] f]
  (let [step 0.001]
    (reduce + (map (fn [x] (* step (f x)))
                   (range l r step)))))

(def f-params (partition 2 (interleave (read-int-list) (read-int-list))))
(def limits (read-int-list))
(println (integral limits (f-area f-params)))
(println (integral limits (f-volume f-params)))


; test cases
(def params1 (partition 2 (interleave [1 2] [0 1])))
(def params2 (partition 2 (interleave [1 2 3 4 5] [6 7 8 9 10])))
; area under the curve
(integral [2 20] (f-area params1))
(integral [1 4] (f-area params2))
; volume of revolving curve
(integral [2 20] (f-volume params1))
(integral [1 4] (f-volume params2))


(defn mid [x step]
  (/ (+ x x step) 2))

; ---------------------------

(defn is-function [pairs]
  (if (= (count pairs)
         (count (into {} pairs)))
      "YES"
      "NO"))

(dotimes [test-case-count (read-int)]
  (let [pairs (read-int-lines (read-int))]
    (println (is-function pairs))))

(is-function [[1 1] [2 2] [3 3]])
(is-function [[1 1] [2 2] [2 3]])

; ---------------------------

(defn distance [[a1 a2] [b1 b2]]
  (Math/sqrt (+ (Math/pow (- a1 b1) 2)
                (Math/pow (- a2 b2) 2))))

(defn perimeter [points]
  (if (< (count points) 2)
      0
      (+ (distance (first points) (second points))
         (perimeter (rest points)))))

(def points [[0 0] [0 1] [1 1] [1 0]])
(distance [0 0] [3 4])
(perimeter (conj points (first points)))

; ---------------------------

(defn area-numerator [points]
  (if (< (count points) 2)
      0
      (let [[[a1 a2] [b1 b2] & _] points]
        (+ (- (* a1 b2) (* b1 a2))
           (area-numerator (rest points))))))

(defn area [points]
  (Math/abs (/ (area-numerator points) 2.0)))

(def points2 [[0 0] [0 1] [1 1] [1 0]])
(area (conj points2 (first points2)))

; ---------------------------

(defn gcd [m n]
  (cond (> m n) (gcd (- m n) n)
        (< m n) (gcd m (- n m))
        :else m))

(gcd 10 5)

; ---------------------------

(defn fib [n]
  (cond (< n 2) 0
        (= n 2) 1
        :else (+ (fib (- n 1)) (fib (- n 2)))))

(defn fib2
  ([n]       (fib2 n 1 0))
  ([n n1 n2] (if (< n 2)
                 n2
                 (fib2 (dec n) (+ n1 n2) n1))))

(for [n [1 2 3 4 5]]
  (fib2 n))

; ---------------------------

(defn fact2 [n]
  (reduce * 1 (range 1 (inc n))))

(defn pascal-row [n]
  (map (fn [c] (/ (fact2 n)
                  (* (fact2 c) (fact2 (- n c)))))
       (range (inc n))))

(defn pascal-triangle [n]
  (map #(pascal-row %) (range n)))

(pascal-triangle 4)
(clojure.string/join "\n" (map (fn [row] (clojure.string/join " " row))
                               (pascal-triangle 4)))

; ---------------------------

(defn print-x [x n]
  (clojure.string/join (repeat n x)))

(defn triangle
  ([n]           (triangle n 32 63))
  ([n rows cols] (map (fn [row]
                        (str (print-x 0 (/ (- cols (+ 1 (* 2 row))) 2))
                             (print-x 1 (+ 1 (* 2 row)))
                             (print-x 0 (/ (- cols (+ 1 (* 2 row))) 2))))
                      (range rows))))

(triangle 0)
