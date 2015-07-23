'use strict';

angular.module('yojmbApp')
    .controller('TargetController', function ($scope, Target, ParseLinks) {
        $scope.targets = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Target.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.targets = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Target.get({id: id}, function(result) {
                $scope.target = result;
                $('#deleteTargetConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Target.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteTargetConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.target = {name: null, id: null};
        };
    });
