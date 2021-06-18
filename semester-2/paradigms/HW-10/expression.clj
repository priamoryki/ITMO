; -----HW 9----- ;

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
   'sum    sum,
   'avg    avg
   '+      add,
   '-      subtract,
   '*      multiply,
   '/      divide})

(defn parse [expression]
  (cond
    (seq? expression) (apply (operations (first expression)) (map parse (rest expression)))
    (contains? variables expression) (variables expression)
    (number? expression) (constant expression)))

(defn parseFunction [expression]
  (parse (read-string expression)))

; -----HW 10----- ;
; :NOTE: proto.clj
(defn p-get [obj key]
  (cond
    (contains? obj key) (obj key)
    (contains? obj :prototype) (p-get (obj :prototype) key)
    :else nil))

(defn p-call [this key & args]
  (apply (p-get this key) this args))

(defn method [key]
  (fn [this & args] (apply p-call this key args)))

(defn field [key]
  (fn [this] (p-get this key)))

(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff))

(declare ZERO)
(def constantPrototype
  {:toString (fn [this]
               (format "%.1f" (double ((field :value) this))))
   :evaluate (fn [this _]
               ((field :value) this))
   :diff     (fn [_ _] ZERO)})

(defn Constant [value]
  {:prototype constantPrototype
   :value     value})

(def ZERO (Constant 0))
(def ONE (Constant 1))

(def variablePrototype
  {:toString (fn [this]
               ((field :value) this))
   :evaluate (fn [this id]
               (id ((field :value) this)))
   :diff     (fn [this id]
               (if (= ((field :value) this) id) ONE ZERO))})

(defn Variable [value]
  {:prototype variablePrototype
   :value     value})

(defn constructor [ctor prototype]
  (fn [& args] (apply ctor {:prototype prototype} args)))

(def operationPrototype (let [components (field :components)]
                          {:toString (fn [this] (str "(" ((field :sign) this) " " (clojure.string/join " " (map toString (components this))) ")"))
                           :evaluate (fn [this vs] (apply ((field :f) this) (mapv #(evaluate % vs) (components this))))
                           :diff     (fn [this diffVar] (((field :differential) this) (components this) (map #(diff % diffVar) (components this))))
                           }))

(defn createOperation [f differential sign]
  (constructor
    (fn [this & args] (assoc this :components args))
    {:prototype    operationPrototype
     :f            f
     :differential differential
     :sign         sign}))

(def Add (createOperation + (fn [args diffVar] (apply Add diffVar)) '+))

(def Sum (createOperation + (fn [args diffVar] (apply Add diffVar)) 'sum))

(def Subtract (createOperation - (fn [args diffVar] (apply Subtract diffVar)) '-))

(def Negate (createOperation - #(Negate ((fn [args diffVar] (apply Add diffVar)) %1 %2)) 'negate))

(declare Multiply)
(defn multiply-diff [args diff-var]
  (nth (reduce (fn [[x dx] [y dy]]
                 [(Multiply x y)
                  (Add (Multiply dx y) (Multiply x dy))])
               [ONE ZERO]
               (mapv vector args diff-var)) 1))

(def Multiply
  (createOperation * multiply-diff '*))

(declare Divide)

(def divide-diff (fn [[x & args] [dx & diffVar]]
                   (if (empty? args)
                     (Divide (Negate dx)
                             (Multiply x x))
                     (let [y (apply Multiply args)
                           dy (multiply-diff args diffVar)] (Divide (Subtract (Multiply dx y) (Multiply x dy)) (Multiply y y))))))

(def Divide (createOperation (fn ([x] (/ 1.0 x)) ([x & ys] (/ (double x) (apply * ys)))) divide-diff '/))

(def Avg (createOperation (fn [& xs] (/ (apply + xs) (count xs)))
                          (fn [args diffVar] (Divide (apply Add diffVar)
                                                     (Constant (count args)))) 'avg))

(def objectVariables
  {'x (Variable "x"),
   'y (Variable "y"),
   'z (Variable "z")})

(def objectOperations
  {'negate Negate
   '+      Add,
   'sum    Sum,
   '-      Subtract,
   '*      Multiply,
   '/      Divide,
   'avg    Avg})

(defn parseExpression [expression]
  (cond
    (seq? expression) (apply (objectOperations (first expression)) (map parseExpression (rest expression)))
    (contains? objectVariables expression) (objectVariables expression)
    (number? expression) (Constant expression)))

(defn parseObject [expression]
  (parseExpression (read-string expression)))