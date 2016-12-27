var myApp = angular.module('myApp', []);

myApp.controller('MainController', ['$scope', '$window', 'dao',
    function($scope, $window, dao) {

        $scope.usersList = dao.getUsers();
            $scope.formSubmit = function() {
            dao.addUser($scope.myName);
            $scope.myName = '';
        }
        $scope.userDelete = function(name) {
            dao.removeUser(name);
        }

    }
]);
