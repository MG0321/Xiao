var redis=require('redis');
//封装一个redisClient类，可以提供一个redis instance和最基本的functions而不是在每个文件中单独使用createClient，会生成n多个client
var client=redis.createClient();
function set(key, value, callback) {
    client.set(key, value, function(err, res) {
        if (err) {
            console.log(err);
            return;
        }
        callback(res);
    });
}
function get(key, callback) {
    client.get(key, function(err, res) {
        if (err) {
            console.log(err);
            return;
        }
        callback(res);
    });
}
//过期时间
function expire(key, timeInSeconds) {
    client.expire(key, timeInSeconds);
}
function quit() {
    client.quit();
}
module.exports = {
    get: get,
    set: set,
    expire: expire,
    quit: quit,
    redisPrint: redis.print
}
