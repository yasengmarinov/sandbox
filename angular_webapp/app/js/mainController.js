'use strict';

var myApp = angular.module('myApp', ['ngRoute'])
    .config(function($routeProvider, $locationProvider) {
        $routeProvider.when('/addUser', {
            templateUrl: 'templates/addUser.html',
            controller: 'MainController'
        });
        $routeProvider.when('/user/:userFirstName', {
            templateUrl: 'templates/userDetails.html',
            controller: 'MainController'
        });
        $routeProvider.otherwise({
            redirectTo: '/addUser'
        });
        $locationProvider.html5Mode(true);
    });

myApp.controller('MainController', ['$scope', '$location', '$window', '$routeParams', 'dao',
    function($scope, $location, $window, $routeParams, dao) {

        $scope.userFirstName = $routeParams.userFirstName;
        if ($routeParams.userFirstName != undefined) {
            dao.getUser($routeParams.userFirstName, function(data) {
                $scope.currentuser = data;
                $scope.$parent.currentuser = data;
            });
        };
        
        var getUsersSuccess = function(data) {
            $scope.usersList = data;
            $scope.$parent.usersList = data;
        };

        $scope.getClass = function(path) {
            return ($location.path().substr(0, path.length) === path) ? 'active' : '';
            console.log($location.path().substr(0, path.length));
            console.log(path);
        };

        dao.getUsers(getUsersSuccess);

        $scope.formSubmit = function() {
            dao.addUser($scope.user, function(data) {
                if (data == true) {
                    dao.getUsers(getUsersSuccess);
                } else {
                    $window.alert("Duplicate user you idiot!");
                }
            });
            $scope.user = undefined;
        }
        $scope.userDelete = function(name) {
            dao.removeUser(name, getUsersSuccess);
        }

    }
]);
