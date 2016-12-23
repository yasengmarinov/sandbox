var myApp = angular.module('myApp', []);

myApp.controller('MainController', ['$scope', function($scope) {
    $scope.page1 = 'page1.html';
    $scope.name = 'Yasen';

    $scope.myName;
    $scope.submitForm = function() {
    }
    
}]);