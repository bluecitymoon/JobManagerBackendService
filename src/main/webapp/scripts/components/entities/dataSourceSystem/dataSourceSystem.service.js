'use strict';

angular.module('yojmbApp')
    .factory('DataSourceSystem', function ($resource, DateUtils) {
        return $resource('api/dataSourceSystems/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
