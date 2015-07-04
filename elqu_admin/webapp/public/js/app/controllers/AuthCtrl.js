app.controller('AuthCtrl', ['$scope', '$log', '$http', '$state', function($scope, $log, $http, $state) {
    $scope.data = {};
    $scope.alerts = [];

    $scope.send = function() {
        $http.post('/api/user/auth', $scope.data)
            .success(function (data) {
				$state.go('reports.files_nspk');
            })
            .error(function (data, status) {
                $scope.alerts = data.items;
				if ( status === 404 )
					$scope.alerts = [{type:"danger", msg:data}];
            });
    };

    // закрыть сообщение
    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };
}]);