/* global monsterSlayerApp */

'use strict';

window.monsterSlayerApp = angular.module('monsterSlayerApp', ['ngRoute']);


monsterSlayerApp.filter('capitalize', function() {
    return function(input) {
      return (!!input) ? input.charAt(0).toUpperCase() + input.substr(1).toLowerCase() : '';
    }
});

monsterSlayerApp.config(function($routeProvider, $httpProvider){
    $routeProvider
        .when("/monster-type",
            {
                templateUrl: 'partials/monster-type.html',
                controller: 'MonsterTypeCtrl',
                resolve: {
                    access: function(Access){return Access.hasOneOfRole(["HERO", "MANAGER"]);}
                }
            })
        .when("/client-request",
            {
                templateUrl: 'partials/client-request.html',
                controller: 'ClientRequestCtrl',
                resolve: {
                    access: function(Access){return Access.isLoggedIn();}
                }
            })
        .when("/job",
            {
                templateUrl: 'partials/job.html',
                controller: 'JobCtrl',
                resolve: {
                    access: function(Access){return Access.isLoggedIn();}
                }
            })
        .when("/user",
            {
                templateUrl: 'partials/user.html',
                controller: 'UserCtrl',
                resolve: {
                    access: function(Access){return Access.hasOneOfRole(["MANAGER"]);}
                }
            })
        .when("/user/detail/:id*",
            {
                templateUrl: 'partials/user-detail.html',
                controller: 'UserDetailCtrl',
                resolve: {
                    access: function(Access){return Access.hasOneOfRole(["HERO", "MANAGER"]);}
                }
            })
        .when("/hero",
            {
                templateUrl: 'partials/hero.html',
                controller: 'HeroCtrl',
                resolve: {
                    access: function(Access){return Access.hasOneOfRole(["HERO"]);}
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
                    access: function(Access){return Access.isLoggedIn();}
                }
            });

    // $httpProvider.interceptors.push('AuthInterceptor');
    $httpProvider.interceptors.push(function ($q) {
        return {
            responseError: function (rejection) {
                console.log(rejection);
                if (rejection.status === 401 && rejection.config.url !== "/pa165/login") {
                    // Something is bad with our credentials,
                    // reload the page to check out the changes in login state.
                    // If this is triggering constant reloading
                    // there might be a backend problem with roles.
                    location.reload();
                }
                return $q.reject(rejection);
            }
        };
        });
});

monsterSlayerApp.factory('Access', function(UserProfile, $q){
    var Access = {
        hasRole: function(role){
            return UserProfile.then(function(profile){
                if(profile === ""){
                    return $q.reject("authentication");
                }
                if(profile.rightsLevel !== role) {
                    return $q.reject("authorization");
                }
                return true;
            });
        },
        hasOneOfRole: function(roles){
            return UserProfile.then(function(profile){
                if(profile === ""){
                    return $q.reject("authentication");
                }
                if(!_.includes(roles, profile.rightsLevel)) {
                    return $q.reject("authorization");
                }
                return true;
            });
        },
        isNotLoggedIn: function(){
            return UserProfile.then(function(profile){
                if(profile !== ""){
                    return $q.reject("not-authentication");
                }

                return true;
            });
        },
        isLoggedIn: function(){
            return UserProfile.then(function(profile){
                if(profile === ""){
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
    $rootScope.loggedUser = null;

    $rootScope.atleastHero = function() {
        return $rootScope.loggedIn && ($rootScope.loggedUser.rightsLevel === "HERO" || $rootScope.atleastManager());
    };

    $rootScope.atleastManager = function() {
        return $rootScope.loggedIn && $rootScope.loggedUser.rightsLevel === "MANAGER";
    };

    $rootScope.userLoggedIn = function(){
        return !!$rootScope.loggedIn;
    };

    UserProfile.then(function(profile){
       $rootScope.loggedUser = profile;
       $rootScope.loggedIn = profile !== "";
       if($rootScope.loggedIn){
           $http.get("/pa165/rest/hero/get-by-user-id/" + $rootScope.loggedUser.id)
               .then(function(data){
               $rootScope.loggedHero = data.data;
           });
       }
    });
});

function prettifyEnum(enumString){
    return _.startCase(_.lowerCase(_.replace(enumString, '_', ' ')));
};
