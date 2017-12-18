angular.module("monsterSlayerApp").directive("ngUploadChange",function(){
    return{
        scope:{
            ngUploadChange:"&"
        },
        link:function($scope, $element){
            $element.on("change",function(event){
                $scope.$apply(function(){
                    $scope.ngUploadChange({$event: event});
                });
            });
            $scope.$on("$destroy",function(){
                $element.off();
            });
        }
    };
});

monsterSlayerApp.controller('UserDetailCtrl', function ($location, $rootScope, $scope, $http) {
    var update = function(){
        $http.get("/pa165/rest" + $location.path()).then(function(response){
            $scope.user = response.data;
        }, function(){
            $rootScope.errorAlert = "Cannot load user detail page";
        });
    };
    
    update();
    
    $scope.upload = function() {
        $('#file').click();  
    };
    
    $scope.fileChanged = function($event) {
        var files = $event.target.files;
        if (files.length !== 1) {
            return;
        }
        var fileReader = new FileReader();
        fileReader.onload = function() {
            var byteArray = [];
            var arrayBuffer = new Uint8Array(this.result);
            arrayBuffer.forEach(function(element) {
                byteArray.push(element);
            });
            $scope.modifyImage($scope.user, files[0].type, byteArray);
        };
        fileReader.readAsArrayBuffer(files[0]);
    };
    
    $scope.modifyImage = function(user, mimeType, image) {
        var data = {
            userId: user.id,
            image: image,
            imageMimeType: mimeType
        };
        if (!mimeType.startsWith("image/")) {
            $rootScope.errorAlert = "You can upload only image files";
            update();
            return;
        }
        $http.put('/pa165/rest/user/modify-image', data)
            .then(function(){
                update();
                $rootScope.successAlert = "Modified profile image of " + user.name;
            },function(){
                $rootScope.errorAlert = "Cannot modify profile image of " + user.name;
            });
    };
});