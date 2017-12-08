'use strict';

const monsterSlayerApp = angular.module('monsterSlayerApp', ['ngRoute']);


monsterSlayerApp.config(function($routeProvider){
    $routeProvider.when("/monster-type", {templateUrl: 'partials/monster-type.html', controller: 'MonsterTypeCtrl'});
    $routeProvider.when("/client-request", {templateUrl: 'partials/client-request.html', controller: 'ClientRequestCtrl'});
    $routeProvider.when("/job", {templateUrl: 'partials/job.html', controller: 'JobCtrl'});
    $routeProvider.when("/user", {templateUrl: 'partials/user.html', controller: 'UserCtrl'});
    $routeProvider.when("/hero", {templateUrl: 'partials/hero.html', controller: 'HeroCtrl'});
});

monsterSlayerApp.controller('MainCtrl', function($scope, $location){

    const updateNavbar = function(){
        $scope.monsterTypeActive = $location.path() === '/monster-type' ? "active" : "";
        $scope.clientRequestActive = $location.path() === '/client-request' ? "active" : "";
        $scope.jobActive = $location.path() === '/job' ? "active" : "";
        $scope.userActive = $location.path() === '/user' ? "active" : "";
        $scope.heroActive = $location.path() === '/hero' ? "active" : "";
    };

    $scope.$on('$routeChangeSuccess', updateNavbar);
    updateNavbar();
});


monsterSlayerApp.controller('MonsterTypeCtrl', function () {
});

monsterSlayerApp.controller('ClientRequestCtrl', function () {
});

monsterSlayerApp.controller('JobCtrl', function ($scope, $http) {
    $http.get('/pa165/rest/job').then(function(response){
        $scope.jobs = response.data;
    });
});

monsterSlayerApp.controller('UserCtrl', function () {
});

monsterSlayerApp.controller('HeroCtrl', function () {
});


monsterSlayerApp.run();
