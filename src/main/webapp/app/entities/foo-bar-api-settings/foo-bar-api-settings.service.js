(function() {
    'use strict';
    angular
        .module('cApp')
        .factory('FooBarAPISettings', FooBarAPISettings);

    FooBarAPISettings.$inject = ['$resource'];

    function FooBarAPISettings ($resource) {
        var resourceUrl =  'api/foo-bar-api-settings/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
