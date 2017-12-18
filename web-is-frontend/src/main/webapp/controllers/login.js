monsterSlayerApp.controller('LoginCtrl', function($scope, $http, $window, $rootScope){
    $scope.login = function(){
        var loginData = {username: $scope.email, password: $scope.password};
        console.log(loginData);

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
});
