'use strict';

angular.module('yojmbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('jobType', {
                parent: 'entity',
                url: '/jobTypes',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'JobTypes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobType/jobTypes.html',
                        controller: 'JobTypeController'
                    }
                },
                resolve: {
                }
            })
            .state('jobType.detail', {
                parent: 'entity',
                url: '/jobType/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'JobType'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/jobType/jobType-detail.html',
                        controller: 'JobTypeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'JobType', function($stateParams, JobType) {
                        return JobType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('jobType.new', {
                parent: 'jobType',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobType/jobType-dialog.html',
                        controller: 'JobTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {description: null, identity: null, column1: null, value1: null, column2: null, value2: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('jobType', null, { reload: true });
                    }, function() {
                        $state.go('jobType');
                    })
                }]
            })
            .state('jobType.edit', {
                parent: 'jobType',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/jobType/jobType-dialog.html',
                        controller: 'JobTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['JobType', function(JobType) {
                                return JobType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('jobType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
