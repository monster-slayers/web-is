/* global monsterSlayerApp */

'use strict';

window.monsterSlayerApp = angular.module('monsterSlayerApp', ['ngRoute']);

monsterSlayerApp.config(function($routeProvider){
    $routeProvider.when("/monster-type", {templateUrl: 'partials/monster-type.html', controller: 'MonsterTypeCtrl'});
    $routeProvider.when("/client-request", {templateUrl: 'partials/client-request.html', controller: 'ClientRequestCtrl'});
    $routeProvider.when("/job", {templateUrl: 'partials/job.html', controller: 'JobCtrl'});
    $routeProvider.when("/user", {templateUrl: 'partials/user.html', controller: 'UserCtrl'});
    $routeProvider.when("/user/detail/:id*", {templateUrl: 'partials/user-detail.html', controller: 'UserDetailCtrl'});
    $routeProvider.when("/hero", {templateUrl: 'partials/hero.html', controller: 'HeroCtrl'});
});

monsterSlayerApp.controller('MainCtrl', function($scope, $location){

    var updateNavbar = function(){
        $scope.monsterTypeActive = $location.path() === '/monster-type' ? "active" : "";
        $scope.clientRequestActive = $location.path() === '/client-request' ? "active" : "";
        $scope.jobActive = $location.path() === '/job' ? "active" : "";
        $scope.userActive = $location.path() === '/user' ? "active" : "";
        $scope.heroActive = $location.path() === '/hero' ? "active" : "";
    };

    $scope.$on('$routeChangeSuccess', updateNavbar);
    updateNavbar();

    $scope.listOfElements = ["NORMAL",
                                 "FIRE",
                                 "WATER",
                                 "EARTH",
                                 "WIND",
                                 "POISON",
                                 "HOLY",
                                 "UNDEAD",
                                 "GHOST"];
});

// add lodash to root scope
monsterSlayerApp.run(function($rootScope){$rootScope._ = _;});

function prettifyEnum(enumString){
    return _.startCase(_.lowerCase(_.replace(enumString, '_', ' ')));
};
