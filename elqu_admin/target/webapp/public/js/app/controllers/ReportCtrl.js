app.controller('ReportCtrl', ['$scope', '$log', '$state', 'ReportService', function($scope, $log, $state, ReportService) {
    'use strict';

    $scope.filter = {
        itemsOnPage: 100,
		currentPage: 1,
		totalItems: 1,
        orderBy: {
            field: '',
            direction: 'asc'
        },
		type: $state.current.data.reportType
    };

	$scope.items = [];

    // обработка ошибок
    var errorHandler = function(data) {
        $scope.alerts = data.items;
    };

	var getReportsData = function(){
		ReportService.getReportData($scope.filter)
			.success(function(data) {
				$scope.items = data.items;
				$scope.filter.totalItems = data.totalItems;
			})
			.error(errorHandler);
	};

    // закрыть сообщение
    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    // функция, вызываемая при изменении сортировки
    $scope.change = function() {
        getReportsData();
    };

	$scope.$watch('filter.itemsOnPage', function(){
		getReportsData();
	});

	$scope.generateFiles = function() {

		var items = [];
		$scope.items.forEach(function(item){
			if ( item.checked )
				items.push(item);
		});
		if ( !items.length ) return false;

		ReportService.generateFiles({items:items})
			.success(errorHandler)
			.error(errorHandler);
	};

}]);