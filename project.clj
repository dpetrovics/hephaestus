(defproject hephaestus "0.1.0"
  :description "Forging quantum goodness"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [net.mikera/core.matrix "0.62.0"]
                 [complex "0.1.12"]]
  :main ^:skip-aot hephaestus.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
