var express = require('express');
var bodyParser = require('body-parser')
var path = require('path');
var app = express();
var rootPath = path.normalize(__dirname + '/../');

// var usersList = ['Yasen', 'Viki', 'John'];
var usersList = [];
app.use(express.static(rootPath + '/app'));
// Add headers
app.use(function (req, res, next) {

    // Website you wish to allow to connect
    res.setHeader('Access-Control-Allow-Origin', '*');

    // Request methods you wish to allow
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');

    // Request headers you wish to allow
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');

    // Set to true if you need the website to include cookies in the requests sent
    // to the API (e.g. in case you use sessions)
    res.setHeader('Access-Control-Allow-Credentials', true);

    // Pass to next layer of middleware
    next();
});
app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

// app.route('/app/*').get(function(req, res) {
//     return res.sendFile((rootPath + '/app/' + 'index.html'));
// });

app.get('/getUsers', function(req, res) {
  res.send(usersList);
});

app.get('/getUser/:firstName', function(req, res) {
  var toFind = req.params.firstName;
  for(var i = 0; i < usersList.length; i++) {
    if (usersList[i].firstName === toFind) {
      res.send(usersList[i]);
    }
  }
});

app.post('/deleteUser', function(req, res) {
  if (req.body.name != undefined) {
     res.send(usersList.splice(usersList.indexOf(req.body.name), 1));
  }
});

app.post('/addUser', function(req, res) {
  var user = req.body.user;
  if (user != undefined) {
      usersList.push(user);
      res.send(true);
  } else {
    res.send(false);
  }
})

app.get('*', function(req, res) {
   return res.redirect(rootPath + '/app/' + 'index.html');
})
app.listen(8000);

console.log("Listening on port 8000");
