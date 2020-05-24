(defproject hephaestus "0.1.0"
  :description "Forging quantum goodness"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [net.littleredcomputer/sicmutils "0.12.1"]
                 [net.mikera/core.matrix "0.62.0"]
                 [prismatic/schema "1.1.12"]
                 [uncomplicate/neanderthal "0.31.0"]
                 [complex "0.1.12"]]
  :main ^:skip-aot hephaestus.core
  :jvm-opts ^:replace [#_"--add-opens=java.base/jdk.internal.ref=ALL-UNNAMED"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[org.clojure/test.check "1.0.0"]]}})
