'use strict';

angular.module('yojmbApp').controller('JobTypeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'JobType', 'Job',
        function($scope, $stateParams, $modalInstance, entity, JobType, Job) {

        $scope.jobType = entity;
        $scope.jobs = Job.query();
        $scope.load = function(id) {
            JobType.get({id : id}, function(result) {
                $scope.jobType = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('yojmbApp:jobTypeUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.jobType.id != null) {
                JobType.update($scope.jobType, onSaveFinished);
            } else {
                JobType.save($scope.jobType, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
