(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('APIServerSettingsDeleteController',APIServerSettingsDeleteController);

    APIServerSettingsDeleteController.$inject = ['$uibModalInstance', 'entity', 'APIServerSettings'];

    function APIServerSettingsDeleteController($uibModalInstance, entity, APIServerSettings) {
        var vm = this;

        vm.aPIServerSettings = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            APIServerSettings.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
