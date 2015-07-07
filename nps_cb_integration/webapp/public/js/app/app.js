var app = angular.module('app', ['ui.bootstrap', 'ngAnimate', 'ui.router', 'angular-loading-bar'])
	.run(['$rootScope', '$state', '$stateParams',
		function ($rootScope,   $state,   $stateParams) {
			'use strict';

			$rootScope.$state = $state;
			$rootScope.$stateParams = $stateParams;

		}])
	.config(['$stateProvider', '$urlRouterProvider', '$httpProvider', '$provide', '$locationProvider', function($stateProvider, $urlRouterProvider, $httpProvider, $provide, $locationProvider) {

		$provide.factory('authHttpInterceptor', function($q) {
			return {
				// optional method
				'request': function(config) {
					// do something on success
					return config;
				},

				// optional method
				'requestError': function(rejection) {
					return $q.reject(rejection);
				},

				// optional method
				'response': function(response) {
					// do something on success
					return response;
				},

				// optional method
				'responseError': function(rejection) {

					var status = rejection.status;

					// если ошибка авторизации
					if ( status === 401 ) {

						window.location = "/#/auth";

						// фиксим баг у индикатора загрузки
						window.location.reload();

						return;
					}

					// otherwise
					return $q.reject(rejection);
				}
			};
		});

		$httpProvider.interceptors.push('authHttpInterceptor');

		// убираем html5 режим в адресации
		$locationProvider.html5Mode({
			enabled: false,
			requireBase: true
		});

		var tmplPath = "public/views/templates/";

		$urlRouterProvider
			.otherwise('/reports/files_nspk');

		$stateProvider
			.state('auth', {
				url: "/auth",
				templateUrl: tmplPath + 'auth.html',
				controller: 'AuthCtrl'
			})
			.state('404', {
				url: "/404",
				templateUrl: tmplPath + '404.html'
			})
			.state('reports', {
				abstract: true,
				url: '',
				templateUrl: tmplPath + 'reports_main.html',
				controller: 'IndexCtrl',
				resolve: {
					data: ['$q', '$http', function($q, $http){
						var deferred = $q.defer();

						$http.get('/api/user/getCurrentUser')
							.success(function(user) {
								deferred.resolve(user);
							})
							.error(function(data) {
								window.location = "/#/auth";
							});

						return deferred.promise;
					}]
				}
			})
			.state('reports.files_nspk', {
				url: '/reports/files_nspk',
				templateUrl: tmplPath + 'reports_tables.html',
				controller: 'ReportCtrl',
				data: {
					reportType: 'filesNspk'
				}
			})
			.state('reports.files_230', {
				url: '/reports/files_230',
				templateUrl: tmplPath + 'reports_tables.html',
				controller: 'ReportCtrl',
				data: {
					reportType: 'files230'
				}
			})
			.state('reports.docs', {
				url: '/reports/docs',
				templateUrl: tmplPath + 'reports_tables.html',
				controller: 'ReportCtrl',
				data: {
					reportType: 'docs'
				}
			})
			.state('reports.response_ed201', {
				url: '/reports/response_ed201',
				templateUrl: tmplPath + 'reports_tables.html',
				controller: 'ReportCtrl',
				data: {
					reportType: 'responseEd201'
				}
			})
			.state('reports.response_ed231', {
				url: '/reports/response_ed231',
				templateUrl: tmplPath + 'reports_tables.html',
				controller: 'ReportCtrl',
				data: {
					reportType: 'responseEd231'
				}
			});

	}])
	.config(['cfpLoadingBarProvider', function(cfpLoadingBarProvider) {
		'use strict';
		// настраиваем индикацию загрузки
		cfpLoadingBarProvider.latencyThreshold = 100;
	}]);


