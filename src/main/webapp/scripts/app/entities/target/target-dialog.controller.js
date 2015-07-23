'use strict';

angular.module('yojmbApp').controller('TargetDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Target', 'Job',
        function($scope, $stateParams, $modalInstance, entity, Target, Job) {

        $scope.target = entity;
        $scope.jobs = Job.query();
        $scope.load = function(id) {
            Target.get({id : id}, function(result) {
                $scope.target = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('yojmbApp:targetUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.target.id != null) {
                Target.update($scope.target, onSaveFinished);
            } else {
                Target.save($scope.target, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
