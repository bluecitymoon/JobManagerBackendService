'use strict';

angular.module('yojmbApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


