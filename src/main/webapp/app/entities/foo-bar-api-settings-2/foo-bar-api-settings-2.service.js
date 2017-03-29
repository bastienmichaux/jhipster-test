(function() {
    'use strict';
    angular
        .module('cApp')
        .factory('FooBarAPISettings2', FooBarAPISettings2);

    FooBarAPISettings2.$inject = ['$resource'];

    function FooBarAPISettings2 ($resource) {
        var resourceUrl =  'api/foo-bar-api-settings-2-s/:id';

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
