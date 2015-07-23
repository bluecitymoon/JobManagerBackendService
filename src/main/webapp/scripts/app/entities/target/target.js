'use strict';

angular.module('yojmbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('target', {
                parent: 'entity',
                url: '/targets',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Targets'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/target/targets.html',
                        controller: 'TargetController'
                    }
                },
                resolve: {
                }
            })
            .state('target.detail', {
                parent: 'entity',
                url: '/target/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Target'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/target/target-detail.html',
                        controller: 'TargetDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Target', function($stateParams, Target) {
                        return Target.get({id : $stateParams.id});
                    }]
                }
            })
            .state('target.new', {
                parent: 'target',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/target/target-dialog.html',
                        controller: 'TargetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('target', null, { reload: true });
                    }, function() {
                        $state.go('target');
                    })
                }]
            })
            .state('target.edit', {
                parent: 'target',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/target/target-dialog.html',
                        controller: 'TargetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Target', function(Target) {
                                return Target.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('target', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
