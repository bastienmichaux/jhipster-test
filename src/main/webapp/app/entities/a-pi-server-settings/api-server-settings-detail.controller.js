(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('APIServerSettingsDetailController', APIServerSettingsDetailController);

    APIServerSettingsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'APIServerSettings'];

    function APIServerSettingsDetailController($scope, $rootScope, $stateParams, previousState, entity, APIServerSettings) {
        var vm = this;

        vm.aPIServerSettings = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cApp:aPIServerSettingsUpdate', function(event, result) {
            vm.aPIServerSettings = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
