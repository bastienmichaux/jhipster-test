(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('PreferredServerAPISettingsDialogController', PreferredServerAPISettingsDialogController);

    PreferredServerAPISettingsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PreferredServerAPISettings'];

    function PreferredServerAPISettingsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PreferredServerAPISettings) {
        var vm = this;

        vm.preferredServerAPISettings = entity;
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
            if (vm.preferredServerAPISettings.id !== null) {
                PreferredServerAPISettings.update(vm.preferredServerAPISettings, onSaveSuccess, onSaveError);
            } else {
                PreferredServerAPISettings.save(vm.preferredServerAPISettings, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cApp:preferredServerAPISettingsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
