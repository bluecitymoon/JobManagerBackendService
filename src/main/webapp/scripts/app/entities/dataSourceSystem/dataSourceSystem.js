'use strict';

angular.module('yojmbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('dataSourceSystem', {
                parent: 'entity',
                url: '/dataSourceSystems',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'DataSourceSystems'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dataSourceSystem/dataSourceSystems.html',
                        controller: 'DataSourceSystemController'
                    }
                },
                resolve: {
                }
            })
            .state('dataSourceSystem.detail', {
                parent: 'entity',
                url: '/dataSourceSystem/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'DataSourceSystem'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/dataSourceSystem/dataSourceSystem-detail.html',
                        controller: 'DataSourceSystemDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DataSourceSystem', function($stateParams, DataSourceSystem) {
                        return DataSourceSystem.get({id : $stateParams.id});
                    }]
                }
            })
            .state('dataSourceSystem.new', {
                parent: 'dataSourceSystem',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dataSourceSystem/dataSourceSystem-dialog.html',
                        controller: 'DataSourceSystemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, identity: null, loginPageUrl: null, queryPageUrl: null, queryParameter1: null, queryParameter2: null, queryParameter3: null, singleDetailUrl: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('dataSourceSystem', null, { reload: true });
                    }, function() {
                        $state.go('dataSourceSystem');
                    })
                }]
            })
            .state('dataSourceSystem.edit', {
                parent: 'dataSourceSystem',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/dataSourceSystem/dataSourceSystem-dialog.html',
                        controller: 'DataSourceSystemDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DataSourceSystem', function(DataSourceSystem) {
                                return DataSourceSystem.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('dataSourceSystem', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
