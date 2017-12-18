monsterSlayerApp.controller('LoginCtrl', function($scope, $http, $window, $rootScope){
    $scope.login = function(){
        var loginData = {username: $scope.email, password: $scope.password};

        $http.post('/pa165/login', loginData, { headers: {'Content-Type': 'application/x-www-form-urlencoded'},
    transformRequest: function(obj) {
        var str = [];
        for(var p in obj)
        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        return str.join("&");
    }}).then(
            function(){
                $window.location.reload();
            },
            function(){
                $rootScope.errorAlert = "Invalid user/password combination!";
            }
        );
    };

    $scope.sampleLogins = [
        {email: "david@client.com", password: "client", role: "Client"},
        {email: "ondra@hero.com", password: "hero", role: "Hero"},
        {email: "tomas@hero.com", password: "hero", role: "Hero"},
        {email: "maksym@manager.com", password: "manager", role: "Manager"},
    ];

    $scope.fillIn = function(user){
        $scope.email = user.email;
        $scope.password  = user.password;
    }
});
