monsterSlayerApp.controller('MonsterTypeCtrl', function ($scope, $http) {
    var update = function(){
        $http.get('/pa165/rest/monster-type').then(function(response){
            $scope.monsterTypes = response.data;
        }, function(){
            //TODO
            console.log('error');
        });
    };

    update();

    //CREATE
    $scope.createMonster = function(name, food, weakness){
        $http.post('/pa165/rest/monster-type/create/' + name + "/" + food + "/" + weakness)
            .then(function(){
                update();
            },function(){
                //TODO
                console.log("error");
            });
    };

    $scope.createMonsterStart = function(){
        $scope.name = "";
        $scope.food = "";
        $scope.selectElements = $scope.$parent.listOfElements[0];
        $scope.createMonsterForm.$setPristine();
        $('#createModal').modal();

    };

    $scope.createMonsterSubmit = function(){
        $scope.createMonster ($scope.name, $scope.food, $scope.selectElements);
        $("#createModal").modal('hide');
    };

    //MODIFY
    $scope.modifyMonster = function(monster, name, food, weaknesses){
        $http.put('/pa165/rest/monster-type/modify/' + monster.id + "/" + name + "/" + food + "/" + weaknesses)
            .then(function(){
                update();
            },function(){
                //TODO
                console.log("error");
        });
    };

    $scope.modifyMonsterStart = function(monster){
        $scope.currentMonster = monster;
        $scope.name = monster.name;
        $scope.food = monster.food;
        $scope.selected = monster.weaknesses.slice();
        $scope.modifyMonsterForm.$setPristine();
        $('#modifyModal').modal();
    };

    $scope.modifyMonsterSubmit = function(){
        $scope.modifyMonster ($scope.currentMonster, $scope.name, $scope.food, $scope.selected);
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
