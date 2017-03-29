(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('APISettingsDialogController', APISettingsDialogController);

    APISettingsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'APISettings'];

    function APISettingsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, APISettings) {
        var vm = this;

        vm.aPISettings = entity;
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
            if (vm.aPISettings.id !== null) {
                APISettings.update(vm.aPISettings, onSaveSuccess, onSaveError);
            } else {
                APISettings.save(vm.aPISettings, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cApp:aPISettingsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
