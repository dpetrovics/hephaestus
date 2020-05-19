(ns hephaestus.core
  (:require [clojure.spec.alpha :as s]
            [clojure.core.matrix :as m]
            [complex.core :as c]))

;; Spec Docs: https://clojure.org/guides/spec

;; Core.Matrix: https://github.com/mikera/core.matrix/wiki/Getting-Started-Guide

;; Complex: https://github.com/alanforr/complex

;; Just for fun, I added a dependency to Neanderthal, which is supposedly an
;; excellent matrix library for Clojure.
;;
;; To actually use it, we'll need to get the Intel Math Kernel library
;; installed, as described here:
;; https://neanderthal.uncomplicate.org/articles/getting_started.html#installation
;;
;; But I think we can work up to that.

(def idnty
  [[(c/complex 1) (c/complex 0)]
   [(c/complex 0) (c/complex 1)]])

(defn conj-transpose
  [mtx]
  (m/emap c/conjugate (m/transpose mtx)))
