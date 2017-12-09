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
    const prettifyJobStatus = function(jobStatus){
        return _.startCase(_.lowerCase(_.replace(jobStatus, '_', ' ')));
    };

    const update = function(){
        $http.get('/pa165/rest/job').then(function(response){
            $scope.jobs = response.data;
            _.each($scope.jobs, function(job){
                job.editable = false;
                job.status = prettifyJobStatus(job.status);
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
        job.status = prettifyJobStatus(newStatus);
    };

    $scope.updateEvaluation = function(job, newEvaluation){
        $http.put('/pa165/rest/job/update-evaluation/' + job.id + "/" + newEvaluation)
            .then(function(response){
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

monsterSlayerApp.controller('UserCtrl', function () {
});

monsterSlayerApp.controller('HeroCtrl', function () {
});

// add lodash to root scope
monsterSlayerApp.run(function($rootScope){$rootScope._ = _;});
