/* global monsterSlayerApp */

'use strict';

window.monsterSlayerApp = angular.module('monsterSlayerApp', ['ngRoute']);

monsterSlayerApp.config(function($routeProvider, $httpProvider, $qProvider){
    // $qProvider.errorOnUnhandledRejections(false);
    $routeProvider
        .when("/monster-type",
            {
                templateUrl: 'partials/monster-type.html',
                controller: 'MonsterTypeCtrl',
                resolve: {
                    access: function(Access){return Access.hasRole("CLIENT");}
                }
            })
        .when("/client-request",
            {
                templateUrl: 'partials/client-request.html',
                controller: 'ClientRequestCtrl',
                resolve: {
                    access: function(Access){return Access.hasRole("CLIENT");}
                }
            })
        .when("/job",
            {
                templateUrl: 'partials/job.html',
                controller: 'JobCtrl',
                resolve: {
                    access: function(Access){return Access.hasRole("CLIENT");}
                }
            })
        .when("/user",
            {
                templateUrl: 'partials/user.html',
                controller: 'UserCtrl',
                resolve: {
                    access: function(Access){return Access.hasRole("CLIENT");}
                }
            })
        .when("/user/detail/:id*",
            {
                templateUrl: 'partials/user-detail.html',
                controller: 'UserDetailCtrl',
                resolve: {
                    access: function(Access){return Access.hasRole("CLIENT");}
                }
            })
        .when("/hero",
            {
                templateUrl: 'partials/hero.html',
                controller: 'HeroCtrl',
                resolve: {
                    access: function(Access){return Access.hasRole("CLIENT");}
                }
            })
        .when("/login",
            {
                templateUrl: 'partials/login.html',
                controller: "LoginCtrl",
                resolve: {
                    access: function(Access){return Access.isNotLoggedIn();}
                }
            })
        .when("/",
            {
                template: "Welcome to Monster slayers' web IS!",
                resolve: {
                    access: function(Access){return Access.hasRole("CLIENT");}
                }
            });

    // $httpProvider.interceptors.push('AuthInterceptor');
    $httpProvider.interceptors.push(function ($q) {
        return {
            responseError: function (rejection) {
                // if (rejection.status === 401) {
                //     location.reload();
                // }
                return $q.reject(rejection);
            }
        };
        });
});

monsterSlayerApp.factory('Access', function(UserProfile, $q){
    var Access = {
        hasRole: function(role){
            return UserProfile.then(function(profile){
                if(profile.email === null){
                    return $q.reject("authentication");
                }
                if(!_.includes(profile.roles, role)) {
                    return $q.reject("authorization");
                }
                return true;
            });
        },
        isNotLoggedIn: function(){
            return UserProfile.then(function(profile){
                if(profile.email !== null){
                    return $q.reject("not-authentication");
                }

                return true;
            });
        }
    };

    return Access;
});

monsterSlayerApp.factory('UserProfile', function(Auth){
    return Auth.getProfile().then(function(data){
        return data.data;
    });
});

monsterSlayerApp.service("Auth", function($http){
   this.getProfile = function(){
       return $http.get("/pa165/rest/user/current");
   };
});

monsterSlayerApp.controller('MainCtrl', function($scope, $location, $http, $window){

    var updateNavbar = function(){
        $scope.hideSuccessAlert();
        $scope.hideErrorAlert();
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

    $scope.logout = function(){
        $http.get("/pa165/logout");
        $window.location.reload();
    };
});

monsterSlayerApp.controller('ClientRequestCtrl', function () {
});

// add lodash to root scope
monsterSlayerApp.run(function($rootScope, $http, $location, UserProfile){
    $rootScope._ = _;

    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideSuccessAlert();


    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
    $rootScope.hideErrorAlert();

    $rootScope.$on('$routeChangeError', function(event, current, previous, rejection) {
        if(rejection === "authentication"){
            $location.path('/login');
        } else if(rejection === "not-authentication"){
            $location.path('/');
        }
    });

    $rootScope.loggedIn = false;
    $rootScope.user = null;

    UserProfile.then(function(profile){
       $rootScope.user = profile;
       $rootScope.loggedIn = profile.email !== null;
    });
});

function prettifyEnum(enumString){
    return _.startCase(_.lowerCase(_.replace(enumString, '_', ' ')));
};
