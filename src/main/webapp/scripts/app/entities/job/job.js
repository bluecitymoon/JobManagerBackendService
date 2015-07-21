'use strict';

angular.module('yojmbApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('job', {
                parent: 'entity',
                url: '/jobs',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Jobs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/job/jobs.html',
                        controller: 'JobController'
                    }
                },
                resolve: {
                }
            })
            .state('job.detail', {
                parent: 'entity',
                url: '/job/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Job'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/job/job-detail.html',
                        controller: 'JobDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Job', function($stateParams, Job) {
                        return Job.get({id : $stateParams.id});
                    }]
                }
            })
            .state('job.new', {
                parent: 'job',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/job/job-dialog.html',
                        controller: 'JobDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {name: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('job', null, { reload: true });
                    }, function() {
                        $state.go('job');
                    })
                }]
            })
            .state('job.edit', {
                parent: 'job',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/job/job-dialog.html',
                        controller: 'JobDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Job', function(Job) {
                                return Job.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('job', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
