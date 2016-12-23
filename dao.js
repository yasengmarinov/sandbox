var dao = angular.module('dao', []);

dao.factory('dao', function($window) {

    var dummyData = ['Yasen', 'Viki', 'John'];

    var getUser = function(name) {
        return 'User ' + name;
    }
    var addUser = function(name) {
        if (dummyData.indexOf(name) == -1) {
            dummyData.push(name);
        } else {
            $window.alert("Duplicate user you idiot!");
        }
    }
    var getUsers = function() {
        return dummyData;
    }
    var removeUser = function(name) {
        dummyData.splice(dummyData.indexOf(name), 1);
    }

    return {
        getUser: function(name) {
            return getUser(name);
        },
        addUser: function(name) {
            addUser(name);
        },
        removeUser: function(name) {
            removeUser(name);
        },
        getUsers: function() {
            return getUsers();
        },
    };

});
