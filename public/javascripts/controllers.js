var chatApp = angular.module('chatApp', []);

chatApp.controller('ChatCtrl', function ($scope) {
  $scope.messages = [];
  $scope.socket = null;

  $scope.username = "1";
  $scope.room = 'room1';

  $scope.join = function(room, username) {
    console.log(room, username);
    if($scope.socket) $scope.socket.close();

    var socket = new WebSocket("ws://localhost:9000/chat/" + room + "?user_name=" + username);
    socket.open = function() { socket.send("hello"); }
    socket.onclose = function() {}
    socket.onmessage = function (e) {
      $scope.$apply(function() { $scope.messages.push(JSON.parse(e.data)); });
    }
    $scope.socket = socket;
  };

  $scope.send = function(message) {
    $scope.socket.send(message);
  }
});

chatApp.controller('UserCtrl', function ($scope) {
  
  $scope.signup = function(email, password) {

      $http({
        method: 'POST',
        url: 'http://127.0.0.1:9000/user/create'
        data: params
      }).success(function(data, status, headers, config) {
        console.log(data)
      }).error(function(data, status, headers, config) {
        console.log(data)
      });
  }


});

