(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('APISettingsDetailController', APISettingsDetailController);

    APISettingsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'APISettings'];

    function APISettingsDetailController($scope, $rootScope, $stateParams, previousState, entity, APISettings) {
        var vm = this;

        vm.aPISettings = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cApp:aPISettingsUpdate', function(event, result) {
            vm.aPISettings = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
