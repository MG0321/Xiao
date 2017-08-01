  ngOnInit() {
    //send id of the problem to server
    this.route.params.subscribe(params => {
      this.sessionId = params['id'];
      this.initEditor();
    });
  }

  initEditor() {
    this.editor = ace.edit('editor');
    this.editor.setTheme('ace/theme/eclipse');
    //reset editor when start
    this.resetEditor();
    this.editor.$blockScrolling = Infinity;
    //focus on the editor of document
    document.getElementsByTagName('textarea')[0].focus();
    this.collaboration.init(this.editor, this.sessionId);
    //apply the changes from other clients
    this.editor.lastAppliedChange = null;
    //apply keyboard change
    this.editor.on('change', (e) => {
      console.log("editor changes: " + JSON.stringify(e));
      if (this.editor.lastAppliedChange != e) {
        this.collaboration.change(JSON.stringify(e));
      }
    })
    //apply cursor change and locate cursor
    this.editor.getSession().getSelection().on("changeCursor", () => {
      let cursor = this.editor.getSession().getSelection().getCursor();
      console.log('cursor moves:' + JSON.stringify(cursor));
      this.collaboration.cursorMove(JSON.stringify(cursor));
    });
    // Restore changes that made by other users
    this.collaboration.restoreBuffer();
  }

router.post("/problems", jsonParser, function (req, res) {
    problemService.addProblem(req.body)
        .then(function (problem) {
            res.json(problem);
        }, function (error) {
            res.status(400).send("Error");
        });
});
router.post('/build_and_run', jsonParser, function (req, res) {
    const userCode = req.body.user_code;
    const lang = req.body.lang;
    console.log(lang + "; " + userCode);
    restClient.methods.build_and_run({
        data: {code: userCode, lang: lang},
        headers: {
            "Content-Type": 'application/json'
        }
    }, (data, response) => {
        console.log("Received from executor server: " + response);
        const text = `Build output:${data['build']}
        Execute output: ${data['run']}`;
        data['text'] = text;
        res.json(data);
    });
})

module.exports = router;



















@app.route("/build_and_run",methods=["POST"])
def build_and_run():
    data=json.loads(request.data.decode('utf-8'))

    if 'code' not in data or 'lang' not in data:
        return "You should provide both 'code' and 'lang'"
    code=data['code']
    lang=data['lang']

    print("API got called with code: %s in %s" % (code, lang))
    result = eu.build_and_run(code, lang)
    return jsonify(result)