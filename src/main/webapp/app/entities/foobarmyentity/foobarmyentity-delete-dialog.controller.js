(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FoobarmyentityDeleteController',FoobarmyentityDeleteController);

    FoobarmyentityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Foobarmyentity'];

    function FoobarmyentityDeleteController($uibModalInstance, entity, Foobarmyentity) {
        var vm = this;

        vm.foobarmyentity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Foobarmyentity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
