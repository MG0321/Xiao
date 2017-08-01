var mongoose=require("mongoose");
//得到schema
var ProblemSchema=mongoose.Schema({
  id:Number,
  name:String,
  desc:String,
  difficulty:String
});
//得到model, 名字为ProblemModel----mongoose的api规定的写法
var problemModel=mongoose.model("ProblemModel",ProblemSchema);

module.exports=problemModel;
