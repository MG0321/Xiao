var express=require("express");//就是去node_modules里面找express这个包并加载
var app=express()
var restRouter=require("./routes/rest");
var mongoose=require("mongoose");
var indexRouter=require("./routes/index")
var path=require("path");
//server要同时serve一个express的实例和一个socket.io的实例，所以不再使用express提供的函数作为服务器，要重新使用http library写一个
var http=require('http');
//socket.io的初始化也是个很tricky的过程，可以从官网上找到使用指南。第一步：引入socket.io
var socket_io=require('socket.io');
//第二步：创建socket.io的instance
var io=socket_io();
//第三步：引入socketService，传入io
var socketService=require('./services/socketService')(io);//初始化io, 因为io是一个单例, 在socketService中被传入
mongoose.connect("mongodb://user:user@ds123410.mlab.com:23410/coj");
//只要请求的是静态文件，请返回当前目录往上一层，public目录底下对应的文件
app.use(express.static(path.join(__dirname,'../public')));
//如果访问的是根目录，那就转到indexRouter
app.use('/',indexRouter);
//所有以/API/v1开头的请求，请都交给restRouter
app.use("/api/v1",restRouter)
//处理problems/id的请求
app.use(function(req,res){
  res.sendFile("index.html",{root:path.join(__dirname,'../public/')});
})
/**
*不再使用express自带的服务器
*/
// app.listen(3000,function(){
// console.log("App is listening to port 3000")
// })
/**
*使用http package中的方法创建服务器
*/
var server=http.createServer(app);
io.attach(server);
server.listen(3000);

server.on('error',onError);
server.on('listening',onListening);

function onError(error){
  throw error;
}
function onListening(){
  var addr=server.address();
  var bind=typeof addr =='string' ? 'pipe: '+addr : 'port: '+addr.port;
  console.log("监听的端口是： "+bind);
}
