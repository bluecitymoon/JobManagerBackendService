'use strict';

angular.module('yojmbApp')
    .controller('DataSourceSystemController', function ($scope, DataSourceSystem, ParseLinks) {
        $scope.dataSourceSystems = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            DataSourceSystem.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.dataSourceSystems = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            DataSourceSystem.get({id: id}, function(result) {
                $scope.dataSourceSystem = result;
                $('#deleteDataSourceSystemConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            DataSourceSystem.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteDataSourceSystemConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.dataSourceSystem = {name: null, identity: null, loginPageUrl: null, queryPageUrl: null, queryParameter1: null, queryParameter2: null, queryParameter3: null, singleDetailUrl: null, id: null};
        };
    });
