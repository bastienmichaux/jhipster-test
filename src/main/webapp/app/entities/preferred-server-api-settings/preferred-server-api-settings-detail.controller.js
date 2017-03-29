(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('PreferredServerAPISettingsDetailController', PreferredServerAPISettingsDetailController);

    PreferredServerAPISettingsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PreferredServerAPISettings'];

    function PreferredServerAPISettingsDetailController($scope, $rootScope, $stateParams, previousState, entity, PreferredServerAPISettings) {
        var vm = this;

        vm.preferredServerAPISettings = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cApp:preferredServerAPISettingsUpdate', function(event, result) {
            vm.preferredServerAPISettings = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
