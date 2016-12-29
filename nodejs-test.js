console.log("KUR");

var doubleNumber = function (num, callback) {
  callback(num*2);
};

var printResults = function (error, result) {
  console.log(null, result);
};

doubleNumber(2, printResults);
