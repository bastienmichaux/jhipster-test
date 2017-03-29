(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('MyAbcDialogController', MyAbcDialogController);

    MyAbcDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MyAbc', 'Author'];

    function MyAbcDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MyAbc, Author) {
        var vm = this;

        vm.myAbc = entity;
        vm.clear = clear;
        vm.save = save;
        vm.authors = Author.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.myAbc.id !== null) {
                MyAbc.update(vm.myAbc, onSaveSuccess, onSaveError);
            } else {
                MyAbc.save(vm.myAbc, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('cApp:myAbcUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
