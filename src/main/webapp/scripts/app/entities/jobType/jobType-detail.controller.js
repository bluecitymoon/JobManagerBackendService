'use strict';

angular.module('yojmbApp')
    .controller('JobTypeDetailController', function ($scope, $rootScope, $stateParams, entity, JobType, Job) {
        $scope.jobType = entity;
        $scope.load = function (id) {
            JobType.get({id: id}, function(result) {
                $scope.jobType = result;
            });
        };
        $rootScope.$on('yojmbApp:jobTypeUpdate', function(event, result) {
            $scope.jobType = result;
        });
    });
