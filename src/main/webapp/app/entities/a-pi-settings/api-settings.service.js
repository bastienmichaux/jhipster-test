(function() {
    'use strict';
    angular
        .module('cApp')
        .factory('APISettings', APISettings);

    APISettings.$inject = ['$resource'];

    function APISettings ($resource) {
        var resourceUrl =  'api/a-pi-settings/:id';

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
