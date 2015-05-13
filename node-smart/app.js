var express = require('express');
var router = express.Router();
var app = express();
var MongoClient = require('mongodb').MongoClient;

app.use(function(req, res, next) {
  res.header("Access-Control-Allow-Origin", "*");
  res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
  next();
});

var db = MongoClient.connect("mongodb://localhost:27017/knx", function(err, db){

  app.get('/events.json', function (req, res) {
    db.collection('events').find().toArray(function (err, items) {
      res.json(items);
    });
  });

});



var server = app.listen(3000, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('App listening at http://%s:%s', host, port);

});