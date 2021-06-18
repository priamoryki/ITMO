(def constant constantly)

(defn variable [value]
  (fn [vars]
    (vars value)))

(defn operation [f]
  (fn [& args]
    (fn [vars]
      (apply f (mapv (fn [g] (g vars)) args)))))

(def negate (operation -))
(def add (operation +))
(def sum add)
(def subtract (operation -))
(def multiply (operation *))

(def divide (operation (fn
                         ([x] (/ 1 (double x)))
                         ([x & ys] (/ x (double (apply * ys)))))))
(def avg (operation (fn [& xs] (/ (apply + xs) (count xs)))))

(def variables
  {'x (variable "x"),
   'y (variable "y"),
   'z (variable "z")})

(def operations
  {'negate negate,
   'sum sum,
   'avg avg
   '+ add,
   '- subtract,
   '* multiply,
   '/ divide})

(defn parse [expression]
  (cond
    (seq? expression) (apply (operations (first expression)) (map parse (rest expression)))
    (contains? variables expression) (variables expression)
    (number? expression) (constant expression)))

(defn parseFunction [expression]
  (parse (read-string expression)))