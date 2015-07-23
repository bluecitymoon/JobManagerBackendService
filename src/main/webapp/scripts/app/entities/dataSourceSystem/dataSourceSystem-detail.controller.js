'use strict';

angular.module('yojmbApp')
    .controller('DataSourceSystemDetailController', function ($scope, $rootScope, $stateParams, entity, DataSourceSystem, Job) {
        $scope.dataSourceSystem = entity;
        $scope.load = function (id) {
            DataSourceSystem.get({id: id}, function(result) {
                $scope.dataSourceSystem = result;
            });
        };
        $rootScope.$on('yojmbApp:dataSourceSystemUpdate', function(event, result) {
            $scope.dataSourceSystem = result;
        });
    });
