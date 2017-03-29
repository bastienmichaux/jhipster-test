(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('UserIPV6AddressDialogController', UserIPV6AddressDialogController);

    UserIPV6AddressDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'UserIPV6Address'];

    function UserIPV6AddressDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, UserIPV6Address) {
        var vm = this;

        vm.userIPV6Address = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.userIPV6Address.id !== null) {
                UserIPV6Address.update(vm.userIPV6Address, onSaveSuccess, onSaveError);
            } else {
                UserIPV6Address.save(vm.userIPV6Address, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cApp:userIPV6AddressUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
