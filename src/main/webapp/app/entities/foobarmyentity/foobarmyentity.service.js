(function() {
    'use strict';
    angular
        .module('cApp')
        .factory('Foobarmyentity', Foobarmyentity);

    Foobarmyentity.$inject = ['$resource'];

    function Foobarmyentity ($resource) {
        var resourceUrl =  'api/foobarmyentities/:id';

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
