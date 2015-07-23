'use strict';

angular.module('yojmbApp').controller('DataSourceSystemDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'DataSourceSystem', 'Job',
        function($scope, $stateParams, $modalInstance, entity, DataSourceSystem, Job) {

        $scope.dataSourceSystem = entity;
        $scope.jobs = Job.query();
        $scope.load = function(id) {
            DataSourceSystem.get({id : id}, function(result) {
                $scope.dataSourceSystem = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('yojmbApp:dataSourceSystemUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.dataSourceSystem.id != null) {
                DataSourceSystem.update($scope.dataSourceSystem, onSaveFinished);
            } else {
                DataSourceSystem.save($scope.dataSourceSystem, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
