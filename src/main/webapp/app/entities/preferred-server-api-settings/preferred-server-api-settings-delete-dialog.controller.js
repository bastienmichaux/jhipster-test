(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('PreferredServerAPISettingsDeleteController',PreferredServerAPISettingsDeleteController);

    PreferredServerAPISettingsDeleteController.$inject = ['$uibModalInstance', 'entity', 'PreferredServerAPISettings'];

    function PreferredServerAPISettingsDeleteController($uibModalInstance, entity, PreferredServerAPISettings) {
        var vm = this;

        vm.preferredServerAPISettings = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PreferredServerAPISettings.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
