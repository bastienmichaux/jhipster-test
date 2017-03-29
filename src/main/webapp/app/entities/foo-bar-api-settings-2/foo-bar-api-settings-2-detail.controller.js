(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FooBarAPISettings2DetailController', FooBarAPISettings2DetailController);

    FooBarAPISettings2DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FooBarAPISettings2'];

    function FooBarAPISettings2DetailController($scope, $rootScope, $stateParams, previousState, entity, FooBarAPISettings2) {
        var vm = this;

        vm.fooBarAPISettings2 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cApp:fooBarAPISettings2Update', function(event, result) {
            vm.fooBarAPISettings2 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
