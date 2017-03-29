(function() {
    'use strict';
    angular
        .module('cApp')
        .factory('APIServerSettings', APIServerSettings);

    APIServerSettings.$inject = ['$resource'];

    function APIServerSettings ($resource) {
        var resourceUrl =  'api/a-pi-server-settings/:id';

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
