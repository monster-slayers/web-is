monsterSlayerApp.controller('HomeCtrl', function ($rootScope) {
    var redirectHome = function(){
        window.location.href = "#!/user/detail/" + $rootScope.loggedUser.id;
    };
    
    redirectHome();
});