'use strict';

angular.module('yojmbApp')
    .controller('JobTypeController', function ($scope, JobType, ParseLinks) {
        $scope.jobTypes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            JobType.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jobTypes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            JobType.get({id: id}, function(result) {
                $scope.jobType = result;
                $('#deleteJobTypeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            JobType.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJobTypeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.jobType = {description: null, identity: null, column1: null, value1: null, column2: null, value2: null, id: null};
        };
    });
