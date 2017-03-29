(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('APISettingsDeleteController',APISettingsDeleteController);

    APISettingsDeleteController.$inject = ['$uibModalInstance', 'entity', 'APISettings'];

    function APISettingsDeleteController($uibModalInstance, entity, APISettings) {
        var vm = this;

        vm.aPISettings = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            APISettings.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
