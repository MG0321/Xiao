var ProblemModel=require("../models/problemModel")
//model是向mongodb通信的时候用的
var getProblems=function(){
    //resolve和reject参数的顺序是固定的对吗？
  return new Promise((resolve,reject)=>{
      /** 1. find是不是异步函数？
      *2. 如果是的话，callback函数的参数是怎样的顺序?固定的？
      *3. 如果不是固定的，程序怎么判定参数到底是err还是problems？
      */
      ProblemModel.find({},function(err,problems){//mongodb的API中本来就有一个find方法，在这里只不过是通过js重写了一个reference。第一个参数是match的pattern，为空，就是所有的都match
        if(err){reject(err);}
        else{
          resolve(problems);
        }
      });
    });
}
var getProblem=function(id){
  return new Promise((resolve,reject)=>{
    // resolve(problems.find(problem=>id===problem.id));
    ProblemModel.findOne({id:id},function(err,problem){//findOne找到第一个match的，条件是id=id。{id:id}是js的object写法，第一个id是problem的string类型属性名id，第二个id是参数
      if(err){reject(err);}
      else{resolve(problem);}
    })
  })
}
var addProblem=function(newProblem){
  return new Promise((resolve,reject)=>{
    // if(problems.find(problem=>problem.name===newProblem.name)){
    //   reject("Problem already exited.");
    // } else{
    //   newProblem.id=problems.length+1;
    //   problems.push(newProblem);
    //   resolve(newProblem);
    // }
    //有没有一个problem的name是我们要添加进来的newProblem的name
    ProblemModel.findOne({name:newProblem.name},function(err,problem){
      if(problem){//=if(problem!=undefined)
        reject("Problem exited!");
      }else{
        ProblemModel.count({},function(err,num){
          newProblem.id=num+1;
          //基于problemModel建立一个mongodb认的problem，内容就是js对象newProblem
          var tmp=new ProblemModel(newProblem);
          //在mongodb中存tmp
          tmp.save();
          resolve(newProblem);
        });
      }
    });
  });
}
//输出一个object
module.exports={
  getProblem:getProblem,
  getProblems:getProblems,
  addProblem:addProblem
}
