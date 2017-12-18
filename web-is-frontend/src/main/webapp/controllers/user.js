monsterSlayerApp.controller('UserCtrl', function ($rootScope, $scope, $http) {
    var update = function(){
        $http.get('/pa165/rest/user').then(function(response){
            $scope.users = response.data;
            _.each($scope.users, function(user){
                user.editable = false;
                user.rightsLevel = prettifyEnum(user.rightsLevel);
                }
            );
        }, function(){
            $rootScope.errorAlert = "Cannot load users";
        });
    };

    update();

    $scope.createHero = function(user, name, elements){
        $http.post('/pa165/rest/hero/create/' + user.id + "/" + name + "/" + elements)
            .then(function(){
                $scope.promoteRights(user, 'HERO');
                $rootScope.successAlert = "Created new hero " + name;
            },function(){
                $rootScope.errorAlert = "Cannot create new hero " + name;
            });
    };

    $scope.createHeroStart = function(user){    
        $scope.currentUser = user;
        $scope.name = "";
        $scope.selected = [];
        $scope.createHeroForm.$setPristine();
        $('#createModal').modal();
    };

    $scope.createHeroSubmit = function(){
        $scope.createHero ($scope.currentUser, $scope.heroName, $scope.selected);
        $("#createModal").modal('hide');
    };

    $scope.promoteRights = function(user, newRightsLevel){
        $http.put('/pa165/rest/user/promote-rights/' + user.id + "/" + newRightsLevel)
            .then(function(){
                user.rightsLevel = prettifyEnum(newRightsLevel);
                $rootScope.successAlert = "Promoted rights of " + user.name + " to " + user.rightsLevel;
            },function(){
                $rootScope.errorAlert = "Cannot promoted rights of " + user.name;
            });
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
});