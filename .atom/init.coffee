# Switch the proto repl to cljs after it is connected
protoRepl.onDidConnect ->
  protoRepl.executeCode("(shadow.cljs.devtools.api/nrepl-select :app)")

atom.commands.add 'atom-text-editor', 'custom:repl-init', ->
  protoRepl.executeCode("(shadow.cljs.devtools.api/nrepl-select :app)")

# Custom command template for the proto repl
atom.commands.add 'atom-text-editor', 'custom:repl-hello', ->
  protoRepl.executeCode("(js/alert \"hello\")")
