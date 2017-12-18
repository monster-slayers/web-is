monsterSlayerApp.controller('JobCtrl', function ($rootScope, $scope, $http) {
    var update = function(){
        $http.get('/pa165/rest/job').then(function(response){
            $scope.jobs = response.data;
            _.each($scope.jobs, function(job){
                job.editable = false;
                job.status = prettifyEnum(job.status);
                }
            );
        }, function(){
            $rootScope.errorAlert = "Cannot load jobs";
        });
    };

    update();

    $scope.updateStatus = function(job, newStatus){
        $http.put('/pa165/rest/job/update-status/' + job.id + "/" + newStatus)
            .then(function(){
                $rootScope.successAlert = "Updated status of job " + job.clientRequest.title;;
            },function(){
                $rootScope.errorAlert = "Cannot update status of job " + job.clientRequest.title;
            });
        job.status = prettifyEnum(newStatus);
    };

    $scope.updateEvaluation = function(job, newEvaluation){
        $http.put('/pa165/rest/job/update-evaluation/' + job.id + "/" + newEvaluation)
            .then(function(){
                $rootScope.successAlert = "Evaluated job " + job.clientRequest.title;
            },function(){
                $rootScope.errorAlert = "Cannot evaluate job " + job.clientRequest.title;
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