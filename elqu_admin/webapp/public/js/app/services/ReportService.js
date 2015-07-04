app.service('ReportService', ['$http', function($http) {
    'use strict';

    var path = '/api/reports/';
	var that = this;

	//данные для отчетов
	that.getReportData = function(data) {
		return $http.post(path + 'getData', data);
	};

	//выгрузка
	that.generateFiles = function(data) {
		return $http.post(path + 'generateFiles', data);
	};


}]);
