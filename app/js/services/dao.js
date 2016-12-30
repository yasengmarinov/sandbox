myApp.factory('dao', function($http) {

    var getUser = function(name) {
        return 'User ' + name;
    }
    var addUser = function(user, callback) {
        $http.post('http://localhost:8000/addUser', {
                'user': user
            })
            .then(function(res) {
                callback(res.data)
            });
    }
    var getUsers = function(callback) {

        $http.get('http://localhost:8000/getUsers').
        then(function(res) {
            callback(res.data)
        });
    };

    var getUser = function(name, callback) {

        $http.get('http://localhost:8000/getUser/' + name).
        then(function(res) {
            callback(res.data)
        });
    };

    var removeUser = function(name, callback) {
        // dummyData.splice(dummyData.indexOf(name), 1);
        $http.post('http://localhost:8000/deleteUser', {
                'name': name
            })
            .then(function() {
                getUsers(callback);
            });
    }

    return {
        getUser: function(name, callback) {
            return getUser(name, callback);
        },
        addUser: function(user, callback) {
            addUser(user, callback);
        },
        removeUser: function(name, callback) {
            removeUser(name, callback);
        },
        getUsers: function(callback) {
            return getUsers(callback);
        },
    };

});
