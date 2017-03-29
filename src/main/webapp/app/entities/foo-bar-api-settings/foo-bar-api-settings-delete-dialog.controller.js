(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FooBarAPISettingsDeleteController',FooBarAPISettingsDeleteController);

    FooBarAPISettingsDeleteController.$inject = ['$uibModalInstance', 'entity', 'FooBarAPISettings'];

    function FooBarAPISettingsDeleteController($uibModalInstance, entity, FooBarAPISettings) {
        var vm = this;

        vm.fooBarAPISettings = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FooBarAPISettings.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
