(ns hephaestus.core
  (:require [clojure.spec.alpha :as s]
            [clojure.core.matrix :as m]
            [complex.core :as c]))

;; Spec Docs: https://clojure.org/guides/spec

;; Core.Matrix: https://github.com/mikera/core.matrix/wiki/Getting-Started-Guide

;; Complex: https://github.com/alanforr/complex

(def idnty [[(c/complex 1) (c/complex 0)]
            [(c/complex 0) (c/complex 1)]])

(defn conj-transpose
  [mtx]
  (m/emap c/conjugate (m/transpose mtx)))
