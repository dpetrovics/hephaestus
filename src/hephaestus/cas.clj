(ns hephaestus.cas
  (:refer-clojure :exclude [partial zero? + - * / ref])
  (:require [sicmutils.env :refer :all]
            [sicmutils.mechanics.lagrange :as lg]))
