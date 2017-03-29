(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FooBarAPISettings2DialogController', FooBarAPISettings2DialogController);

    FooBarAPISettings2DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FooBarAPISettings2'];

    function FooBarAPISettings2DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FooBarAPISettings2) {
        var vm = this;

        vm.fooBarAPISettings2 = entity;
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
            if (vm.fooBarAPISettings2.id !== null) {
                FooBarAPISettings2.update(vm.fooBarAPISettings2, onSaveSuccess, onSaveError);
            } else {
                FooBarAPISettings2.save(vm.fooBarAPISettings2, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cApp:fooBarAPISettings2Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
