'use strict';

angular.module('yojmbApp')
    .controller('JobDetailController', function ($scope, $rootScope, $stateParams, entity, Job) {
        $scope.job = entity;
        $scope.load = function (id) {
            Job.get({id: id}, function(result) {
                $scope.job = result;
            });
        };
        $rootScope.$on('yojmbApp:jobUpdate', function(event, result) {
            $scope.job = result;
        });
    });
