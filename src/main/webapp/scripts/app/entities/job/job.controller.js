'use strict';

angular.module('yojmbApp')
    .controller('JobController', function ($scope, Job, ParseLinks) {
        $scope.jobs = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Job.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.jobs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Job.get({id: id}, function(result) {
                $scope.job = result;
                $('#deleteJobConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Job.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteJobConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.job = {name: null, id: null};
        };
    });
