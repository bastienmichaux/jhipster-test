(function() {
    'use strict';
    angular
        .module('cApp')
        .factory('MyAbc', MyAbc);

    MyAbc.$inject = ['$resource'];

    function MyAbc ($resource) {
        var resourceUrl =  'api/my-abcs/:id';

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
