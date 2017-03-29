(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('UserIPV6AddressDetailController', UserIPV6AddressDetailController);

    UserIPV6AddressDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UserIPV6Address'];

    function UserIPV6AddressDetailController($scope, $rootScope, $stateParams, previousState, entity, UserIPV6Address) {
        var vm = this;

        vm.userIPV6Address = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cApp:userIPV6AddressUpdate', function(event, result) {
            vm.userIPV6Address = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
