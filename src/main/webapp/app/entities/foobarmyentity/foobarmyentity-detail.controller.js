(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FoobarmyentityDetailController', FoobarmyentityDetailController);

    FoobarmyentityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Foobarmyentity'];

    function FoobarmyentityDetailController($scope, $rootScope, $stateParams, previousState, entity, Foobarmyentity) {
        var vm = this;

        vm.foobarmyentity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cApp:foobarmyentityUpdate', function(event, result) {
            vm.foobarmyentity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
