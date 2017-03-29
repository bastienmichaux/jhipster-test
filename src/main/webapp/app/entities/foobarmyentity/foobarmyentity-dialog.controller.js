(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FoobarmyentityDialogController', FoobarmyentityDialogController);

    FoobarmyentityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Foobarmyentity'];

    function FoobarmyentityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Foobarmyentity) {
        var vm = this;

        vm.foobarmyentity = entity;
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
            if (vm.foobarmyentity.id !== null) {
                Foobarmyentity.update(vm.foobarmyentity, onSaveSuccess, onSaveError);
            } else {
                Foobarmyentity.save(vm.foobarmyentity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cApp:foobarmyentityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
