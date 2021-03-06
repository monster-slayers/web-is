monsterSlayerApp.controller('ClientRequestCtrl', function ($rootScope, $scope, $http) {
    var update = function(){
        $http.get('/pa165/rest/client-request').then(function(response){
            var clientRequests = response.data;
            $http.get('/pa165/rest/job').then(function(response){
                var jobs = response.data;
                _.forEach(clientRequests, function(c){
                    c.jobExists = false;
                    c.heroes = [];
                });
                _.forEach(jobs, function(job){
                    var cId = job.clientRequest.id;
                    var c = _.find(clientRequests, function(cc){
                        return cc.id === cId;
                    });

                    c.jobExists = true;
                    c.heroes.push(job.assignee.id);
                });
                $scope.clientRequests = clientRequests;
            });
        }, function(){
            $rootScope.errorAlert = "Cannot load client requests";
        });
    };

    update();

    $scope.removeKillListEntry = function(index){
        if($scope.killList.length > 1){
            $scope.killList.splice(index, 1);
        }
    };

    $scope.addKillListEntry = function(){
        $scope.killList.push({key:-1,value:""})
    };

    // CREATE
    $scope.createClientRequest = function(title, description, location, clientUserId, killList, reward){
        var dataObj = {
                title: title,
                description: description,
                location: location,
                clientId: clientUserId,
                killList: killList,
                reward: reward
        };

        $http.post('/pa165/rest/client-request/create/', dataObj)
            .then(function(){
                update();
                $rootScope.successAlert = "Client Request \"" + title + "\" successfully created."
            },function(){
                $rootScope.errorAlert = "Cannot save client request";
            });
    };

    $scope.createClientRequestStart = function(){
        $http.get('/pa165/rest/user').then(function(response)
            {
                $scope.users = response.data
            },
            function(){
                $rootScope.errorAlert = "Cannot load users";
            });
        $http.get('/pa165/rest/monster-type').then(function(response)
            {
                $scope.monsterTypes = response.data
            },
            function(){
                $rootScope.errorAlert = "Cannot load monsters";
            });
        $scope.title = "";
        $scope.description = "";
        $scope.location = "";
        $scope.clientUserId = $scope.loggedUser.id;
        $scope.killList = [{key:-1,value:""}];
        $scope.reward = "";
        $scope.createClientRequestForm.$setPristine();
        $('#createModal').modal();

    };

    $scope.createClientRequestSubmit = function(){
        $scope.createClientRequest (
            $scope.title,
            $scope.description,
            $scope.location,
            $scope.clientUserId,
            $scope.killList,
            $scope.reward);
        $("#createModal").modal('hide');
    };

    $scope.toggle = function (element) {
        var index = $scope.selected.indexOf(element);
        if (index > -1) {
            $scope.selected.splice(index, 1);
        }
        else {
            $scope.selected.push(element);
        }
    };

    // MODIFY
    $scope.modifyClientRequest = function(current, title, description, location, killList, reward){
        var dataObj = {
            title: title,
            description: description,
            location: location,
            killList: killList,
            reward: reward
        };

        $http.put('/pa165/rest/client-request/modify/'+ current.id, dataObj)
            .then(function(){
                update();
                $rootScope.successAlert = "Client Request \"" + title + "\" successfully saved."
            },function(){
                $rootScope.errorAlert = "Cannot save client request";
            });
    };

    $scope.modifyClientRequestStart = function(request){
        $http.get('/pa165/rest/user').then(function(response)
            {
                $scope.users = response.data
            },
            function(){
                $rootScope.errorAlert = "Cannot load users";
            });
        $http.get('/pa165/rest/monster-type').then(function(response)
            {
                $scope.monsterTypes = response.data
            },
            function(){
                $rootScope.errorAlert = "Cannot load monsters";
            });
        $scope.currentRequest = request;
        $scope.title = request.title;
        $scope.clientUserId = request.client.id;
        $scope.description = request.description;
        $scope.location = request.location;
        $scope.killList = [];
        request.killList.forEach(
            function (entry) {
                $scope.killList.push({ key: entry.key.id, value: entry.value });
            });
        $scope.reward = request.reward;
        $scope.modifyClientRequestForm.$setPristine();
        $('#modifyModal').modal();
    };

    $scope.modifyClientRequestSubmit = function(){
        $scope.modifyClientRequest (
            $scope.currentRequest,
            $scope.title,
            $scope.description,
            $scope.location,
            $scope.killList,
            $scope.reward);
        $("#modifyModal").modal('hide');
    };

    // CREATE JOB
    $scope.createJob = function (currentRequest, assignee) {
        $http.post('/pa165/rest/job/create/' + currentRequest.id + "/" + assignee)
            .then(
                function () {
                    update();
                    $rootScope.successAlert = "Job for Client Request \"" + currentRequest.title + "\" successfully created."
                },
                function() {
                    $rootScope.errorAlert = "Cannot save job";
                });
    };

    $scope.createJobStart = function(request){
        $http.get('/pa165/rest/hero').then(function(response){
            $scope.heroes = response.data});
        $scope.currentRequest = request;
        $http.get('/pa165/rest/client-request/suggested-hero/'+request.id).then(function (response) {
            $scope.suggested = response.data
        });
        $scope.assignee = "";
        $scope.createJobForm.$setPristine();
        $('#createJobModal').modal();
    };

    $scope.selectSuggested = function(suggested) {
        $scope.assignee = $scope.suggested.id;
    };

    $scope.createJobSubmit = function(){
        $scope.createJob (
            $scope.currentRequest,
            $scope.assignee);
        $("#createJobModal").modal('hide');
    };
});
