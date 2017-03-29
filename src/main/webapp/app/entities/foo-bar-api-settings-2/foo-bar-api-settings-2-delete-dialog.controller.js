(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FooBarAPISettings2DeleteController',FooBarAPISettings2DeleteController);

    FooBarAPISettings2DeleteController.$inject = ['$uibModalInstance', 'entity', 'FooBarAPISettings2'];

    function FooBarAPISettings2DeleteController($uibModalInstance, entity, FooBarAPISettings2) {
        var vm = this;

        vm.fooBarAPISettings2 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FooBarAPISettings2.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
