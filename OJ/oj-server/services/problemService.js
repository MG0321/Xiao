var ProblemModel = require("../models/problemModel")
var getProblems = function() {
    return new Promise((resolve, reject) => {
        ProblemModel.find({}, function(err, problems) {
            if (err) { reject(err); } else {
                resolve(problems);
            }
        });
    });
}
var getProblem = function(id) {
    return new Promise((resolve, reject) => {
        // resolve(problems.find(problem=>id===problem.id));
        ProblemModel.findOne({ id: id }, function(err, problem) {
            if (err) { reject(err); } else { resolve(problem); }
        })
    })
}
var addProblem = function(newProblem) {
    return new Promise((resolve, reject) => {
        // if(problems.find(problem=>problem.name===newProblem.name)){
        //   reject("Problem already exited.");
        // } else{
        //   newProblem.id=problems.length+1;
        //   problems.push(newProblem);
        //   resolve(newProblem);
        // }
        ProblemModel.findOne({ name: newProblem.name }, function(err, problem) {
            if (problem) { //=if(problem!=undefined)
                reject("Problem exited!");
            } else {
                ProblemModel.count({}, function(err, num) {
                    newProblem.id = num + 1;
                    var tmp = new ProblemModel(newProblem);
                    tmp.save();
                    resolve(newProblem);
                });
            }
        });
    });
}
module.exports = {
    getProblem: getProblem,
    getProblems: getProblems,
    addProblem: addProblem
}