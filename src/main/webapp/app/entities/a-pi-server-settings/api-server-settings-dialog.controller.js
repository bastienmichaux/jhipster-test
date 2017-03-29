(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('APIServerSettingsDialogController', APIServerSettingsDialogController);

    APIServerSettingsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'APIServerSettings'];

    function APIServerSettingsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, APIServerSettings) {
        var vm = this;

        vm.aPIServerSettings = entity;
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
            if (vm.aPIServerSettings.id !== null) {
                APIServerSettings.update(vm.aPIServerSettings, onSaveSuccess, onSaveError);
            } else {
                APIServerSettings.save(vm.aPIServerSettings, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cApp:aPIServerSettingsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
