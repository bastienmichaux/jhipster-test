(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('MyAbcDeleteController',MyAbcDeleteController);

    MyAbcDeleteController.$inject = ['$uibModalInstance', 'entity', 'MyAbc'];

    function MyAbcDeleteController($uibModalInstance, entity, MyAbc) {
        var vm = this;

        vm.myAbc = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MyAbc.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
