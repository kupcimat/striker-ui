(defproject org.saigon/striker "release"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/clojurescript "1.10.597"
                  :exclusions [com.google.javascript/closure-compiler-unshaded
                               org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [thheller/shadow-cljs "2.8.97"]
                 [reagent "0.10.0"]
                 [re-frame "0.12.0"]
                 [day8.re-frame/http-fx "v0.2.0"]
                 [metosin/reitit-frontend "0.4.2"]]

  :plugins [[lein-shell "0.5.0"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj" "src/cljs"]

  :test-paths   ["test/cljs"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "resources/public/info.json"]

  :shell {:commands {"open" {:windows ["cmd" "/c" "start"]
                             :macosx  "open"
                             :linux   "xdg-open"}}}

  :aliases {"dev"          ["with-profile" "dev" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "watch" "app"]]
            "prod"         ["do"
                            ["with-profile" "dev" "run" "-m" "striker.util/build-info" :project/group :project/name "resources/public/info.json"]
                            ["with-profile" "prod" "run" "-m" "shadow.cljs.devtools.cli" "release" "app"]]
            "build-report" ["with-profile" "prod" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "run" "shadow.cljs.build-report" "app" "target/build-report.html"]
                            ["shell" "open" "target/build-report.html"]]
            "karma"        ["with-profile" "prod" "do"
                            ["run" "-m" "shadow.cljs.devtools.cli" "compile" "karma-test"]
                            ["shell" "./node_modules/karma/bin/karma" "start" "--single-run" "--reporters" "junit,dots"]]}

  :profiles {:dev {:dependencies [[binaryage/devtools "1.0.0"]
                                  [proto-repl "0.3.1"]
                                  [org.clojure/data.json "1.0.0"]]}
             :prod {}}

  :prep-tasks [])
