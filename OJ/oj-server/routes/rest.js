var express = require("express");
var router = express.Router();
var problemService = require("../services/problemService");
var bodyParser = require("body-parser");
var jsonParser = bodyParser.json();
var node_rest_client = require('node-rest-client').Client;
var restClient = new node_rest_client();

EXECUTOR_SERVER_URL = 'http://localhost:5000/build_and_run';

restClient.registerMethod('build_and_run', EXECUTOR_SERVER_URL, 'post');
router.get("/problems", function(req, res) {
    problemService.getProblems()
        .then(problems => res.json(problems))
})
router.get("/problems/:id", function(req, res) {
    console.log(req);
    var id = req.params.id;
    problemService.getProblem(+id)
        .then(problem => res.json(problem))
})
router.post("/problems", jsonParser, function(req, res) {
    problemService.addProblem(req.body)
        .then(function(problem) {
            res.json(problem);
        }, function(error) {
            res.status(400).send("Error");
        });
});
router.post('/build_and_run', jsonParser, function(req, res) {
    const userCode = req.body.user_code;
    const lang = req.body.lang;
    console.log(lang + "; " + userCode);
    restClient.methods.build_and_run({
        data: { code: userCode, lang: lang },
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