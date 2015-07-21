'use strict';

angular.module('yojmbApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
