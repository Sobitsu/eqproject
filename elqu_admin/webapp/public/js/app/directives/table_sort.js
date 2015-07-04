app.directive('sort', ['$parse', function ($parse) {
    'use strict';

    return {
        scope: {
            orderBy: '=',
            field: '@',
            name: '@',
            orderChange: '&'
        },
        templateUrl: '/public/views/templates/table_sort.html',
        restrict: 'E',
        replace: true,

        link: function(scope, element, attrs) {
        },

        controller: function($scope) {

            // если переменная не была определена в $parent
            if (typeof $scope.orderBy === 'undefined') {
                $scope.orderBy = {
                    field: '',
                    direction: ''
                };
            }

            // если direction не определено
            if (typeof $scope.orderBy.direction === 'undefined') {
                $scope.orderBy.direction = '';
            }

            // обрабатываем нажатие на элемент
            $scope.click = function () {
                // текущее состояние сортировки
                var direction = $scope.orderBy.direction;

                // если поле изменилось, устанавливаем asc по умолчанию
                if ($scope.orderBy.field !== $scope.field) {
                    $scope.orderBy = {
                        field: $scope.field,
                        direction: 'asc'
                    };
                } else

                // если было asc ставим в desc
                if (direction === 'asc') {
                    $scope.orderBy.direction = 'desc';
                } else

                // если небыло, ставим asc
                if (direction === '') {
                    $scope.orderBy.direction = 'asc';
                } else

                // если было desc стави в asc
                if (direction === 'desc') {
                    $scope.orderBy.direction = '';
                }

                $scope.orderChange();
            };
        }
    };
}]);