'use strict';

angular.module('yojmbApp').controller('JobDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Job',
        function($scope, $stateParams, $modalInstance, entity, Job) {

        $scope.job = entity;
        $scope.load = function(id) {
            Job.get({id : id}, function(result) {
                $scope.job = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('yojmbApp:jobUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.job.id != null) {
                Job.update($scope.job, onSaveFinished);
            } else {
                Job.save($scope.job, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
