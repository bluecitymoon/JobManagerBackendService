'use strict';

angular.module('yojmbApp')
    .controller('TargetDetailController', function ($scope, $rootScope, $stateParams, entity, Target, Job) {
        $scope.target = entity;
        $scope.load = function (id) {
            Target.get({id: id}, function(result) {
                $scope.target = result;
            });
        };
        $rootScope.$on('yojmbApp:targetUpdate', function(event, result) {
            $scope.target = result;
        });
    });
