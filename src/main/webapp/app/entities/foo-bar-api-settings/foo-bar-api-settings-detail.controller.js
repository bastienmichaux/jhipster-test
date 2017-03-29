(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FooBarAPISettingsDetailController', FooBarAPISettingsDetailController);

    FooBarAPISettingsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FooBarAPISettings'];

    function FooBarAPISettingsDetailController($scope, $rootScope, $stateParams, previousState, entity, FooBarAPISettings) {
        var vm = this;

        vm.fooBarAPISettings = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cApp:fooBarAPISettingsUpdate', function(event, result) {
            vm.fooBarAPISettings = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
