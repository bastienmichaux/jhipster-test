(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('UserIPV6AddressDeleteController',UserIPV6AddressDeleteController);

    UserIPV6AddressDeleteController.$inject = ['$uibModalInstance', 'entity', 'UserIPV6Address'];

    function UserIPV6AddressDeleteController($uibModalInstance, entity, UserIPV6Address) {
        var vm = this;

        vm.userIPV6Address = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UserIPV6Address.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
