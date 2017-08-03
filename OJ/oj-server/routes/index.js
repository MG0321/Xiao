var express = require("express");
var router = express.Router();
var path = require("path");
router.get('/', function(req, res) {
    //sendFile是express library中的一个函数
    res.sendFile("index.html", { root: path.join(__dirname, '../../public/') });
});

module.exports = router;