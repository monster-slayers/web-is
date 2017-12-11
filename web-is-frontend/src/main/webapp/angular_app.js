'use strict';

var monsterSlayerApp = angular.module('monsterSlayerApp', ['ngRoute']);

monsterSlayerApp.config(function($routeProvider){
    $routeProvider.when("/monster-type", {templateUrl: 'partials/monster-type.html', controller: 'MonsterTypeCtrl'});
    $routeProvider.when("/client-request", {templateUrl: 'partials/client-request.html', controller: 'ClientRequestCtrl'});
    $routeProvider.when("/job", {templateUrl: 'partials/job.html', controller: 'JobCtrl'});
    $routeProvider.when("/user", {templateUrl: 'partials/user.html', controller: 'UserCtrl'});
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
});


monsterSlayerApp.controller('MonsterTypeCtrl', function () {
});

monsterSlayerApp.controller('ClientRequestCtrl', function () {
});

monsterSlayerApp.controller('JobCtrl', function ($scope, $http) {
    var update = function(){
        $http.get('/pa165/rest/job').then(function(response){
            $scope.jobs = response.data;
            _.each($scope.jobs, function(job){
                job.editable = false;
                job.status = prettifyEnum(job.status);
                }
            );
        }, function(){
            //TODO
            console.log('error');
        });
    };

    update();

    $scope.updateStatus = function(job, newStatus){
        $http.put('/pa165/rest/job/update-status/' + job.id + "/" + newStatus)
             .then(function(response){
             },function(){
                 //TODO
                 console.log("error");
             });
        job.status = prettifyEnum(newStatus);
    };

    $scope.updateEvaluation = function(job, newEvaluation){
        $http.put('/pa165/rest/job/update-evaluation/' + job.id + "/" + newEvaluation)
            .then(function(){
            },function(){
                //TODO
                console.log("error");
            });
        job.evaluation = newEvaluation;
    };

    $scope.evaluateJob = function(job){
        $scope.jobToBeEvaluated = job;
        $('#doneModal').modal();
    };

    $scope.evaluate = function(){
        if(!$scope.evaluationForm.evaluation.$valid){
            console.log('invalid');
            return;
        }
        $scope.updateEvaluation($scope.jobToBeEvaluated, $scope.evaluation);

        $("#doneModal").modal('hide');
    };

});

monsterSlayerApp.controller('UserCtrl', function ($scope, $http) {
    var update = function(){
        $http.get('/pa165/rest/user').then(function(response){
            $scope.users = response.data;
            _.each($scope.users, function(user){
                user.editable = false;
                user.rightsLevel = prettifyEnum(user.rightsLevel);
                }
            );
        }, function(){
            //TODO
            console.log('error');
        });
    };

    update();

    $scope.createHero = function(user, name){
        $scope.promoteRights(user, 'HERO');
        $http.post('/pa165/rest/hero/create/' + user.id + "/" + name)
             .then(function(response){
             },function(){
                 //TODO
                 console.log("error");
             });
    };

    $scope.createHeroStart = function(user){
        $scope.currentUser = user;
        $('#doneModal').modal();
    };

    $scope.createHeroSubmit = function(){
        $scope.createHero ($scope.currentUser, $scope.heroName);
        $("#doneModal").modal('hide');
    };

    $scope.promoteRights = function(user, newRightsLevel){
        $http.put('/pa165/rest/user/promote-rights/' + user.id + "/" + newRightsLevel)
             .then(function(response){
             },function(){
                 //TODO
                 console.log("error");
             });
        user.rightsLevel = prettifyEnum(newRightsLevel);
    };
});

monsterSlayerApp.controller('HeroCtrl', function ($scope, $http) {
    var update = function(){
        $http.get('/pa165/rest/hero').then(function(response){
            $scope.heroes = response.data;
            _.each($scope.heroes, function(hero){
                hero.status = prettifyEnum(hero.status);
            });
        }, function(){
            //TODO
            console.log('error');
        });
    };

    update();

    $scope.deleteHero = function(hero){
        $http.delete('/pa165/rest/hero/delete/' + hero.id)
             .then(function(){
                 update();
             },function(){
                 //TODO
                 console.log("error");
             });
    };

    $scope.changeStatus = function(hero, newStatus){
        $http.put('/pa165/rest/hero/change-status/' + hero.id + "/" + newStatus)
            .then(function(){
                hero.status = prettifyEnum(newStatus);
            },function(){
                //TODO
                console.log("error");
            });
        $("#killModal").modal('hide');
    };

    $scope.killHero = function(hero){
        $scope.heroToBeKilled = hero;
        console.log('adf');
        $("#killModal").modal();
    };
});

// add lodash to root scope
monsterSlayerApp.run(function($rootScope){$rootScope._ = _;});

function prettifyEnum(enumString){
    return _.startCase(_.lowerCase(_.replace(enumString, '_', ' ')));
};
