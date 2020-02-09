(set-env!
  :resource-paths #{"src" "resources"}
  :dependencies '[[org.clojure/clojure "1.10.1"]
                  [org.clojure/core.async "0.7.559"]
                  [org.clojure/math.numeric-tower "0.0.4"]
                  [environ "1.1.0"]
                  [boot-environ "1.1.0"]])

(require '[environ.boot :refer [environ]])

(task-options!
  environ {:env {}}
  pom {:project 'tinyrender
       :version "0.1.0-SNAPSHOT"}
  jar {:main 'tinyrender.core
       :file "tinyrender.jar"}
  aot {:all true})

(deftask dev
  []
  (set-env! :resource-paths #(conj % "dev")
            :dependencies #(into [] (concat % '[])))
  (task-options!
    repl {:init-ns 'user})
  (environ))

(deftask build
  "Build it"
  []
  (comp (aot) 
        (pom) 
        (uber) 
        (jar) 
        (target)))

;; vim: set ft=clojure:
