var chatApp = angular.module('chatApp', []);

chatApp.controller('ChatCtrl', function ($scope) {
  $scope.messages = [];
  $scope.socket = null;

  $scope.username = "1";
  $scope.room = 'Lobby1';

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

