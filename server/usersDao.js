var fs = require('fs');

module.exports.get = function(req, res) {
  var users = fs.readFileSync('app/data/users.json', 'utf8');
  console.log(users);
  res.send(JSON.parse(users));
};

module.exports.save = function(req, res) {
  var user = req.body.user;
  if (user != undefined) {
    fs.writeFileSync('app/data/users.json', JSON.stringify(user));
      res.send(true);
  } else {
    res.send(false);
  }
};
