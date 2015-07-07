app.controller('IndexCtrl', ['$scope', '$log', '$http', '$state', 'data', function($scope, $log, $http, $state, data) {
    $scope.user = data.user || {};

    $scope.exit = function() {
        $http.get('/api/user/logout')
            .success(function() {
                $state.go('auth');
            })
            .error(function(err) {
                console.log(err);
            });
    };

    $scope.closeAlert = function(alerts, index) {
        alerts.splice(index, 1);
    };

    $scope.errorHandler = function(data) {
        $scope.alerts = data.items;
    };
}]);