(function() {
    'use strict';
    angular
        .module('cApp')
        .factory('UserIPV6Address', UserIPV6Address);

    UserIPV6Address.$inject = ['$resource'];

    function UserIPV6Address ($resource) {
        var resourceUrl =  'api/user-ipv-6-addresses/:id';

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
