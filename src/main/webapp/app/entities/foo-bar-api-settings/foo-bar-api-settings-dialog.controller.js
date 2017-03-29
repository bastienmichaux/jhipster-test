(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FooBarAPISettingsDialogController', FooBarAPISettingsDialogController);

    FooBarAPISettingsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FooBarAPISettings'];

    function FooBarAPISettingsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FooBarAPISettings) {
        var vm = this;

        vm.fooBarAPISettings = entity;
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
            if (vm.fooBarAPISettings.id !== null) {
                FooBarAPISettings.update(vm.fooBarAPISettings, onSaveSuccess, onSaveError);
            } else {
                FooBarAPISettings.save(vm.fooBarAPISettings, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cApp:fooBarAPISettingsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
