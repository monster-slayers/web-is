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
        $("#killModal").modal();
    };
    
    $scope.modifyHero = function(hero, name, elements){
        $http.put('/pa165/rest/hero/modify/' + hero.id + "/" + name + "/" + elements)
             .then(function(){
                 update();
             },function(){
                 //TODO
                 console.log("error");
             });
    };

    $scope.modifyHeroStart = function(hero){
        $scope.currentHero = hero;
        $scope.name = hero.heroName;
        $scope.selected = hero.elements.slice();
        $scope.modifyHeroForm.$setPristine();
        $('#modifyModal').modal();
    };

    $scope.modifyHeroSubmit = function(){
        $scope.modifyHero($scope.currentHero, $scope.name, $scope.selected);
        $("#modifyModal").modal('hide');
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