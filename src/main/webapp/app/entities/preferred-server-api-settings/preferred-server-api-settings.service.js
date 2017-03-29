(function() {
    'use strict';
    angular
        .module('cApp')
        .factory('PreferredServerAPISettings', PreferredServerAPISettings);

    PreferredServerAPISettings.$inject = ['$resource'];

    function PreferredServerAPISettings ($resource) {
        var resourceUrl =  'api/preferred-server-api-settings/:id';

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
