(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('MyAbcDetailController', MyAbcDetailController);

    MyAbcDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MyAbc', 'Author'];

    function MyAbcDetailController($scope, $rootScope, $stateParams, previousState, entity, MyAbc, Author) {
        var vm = this;

        vm.myAbc = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('cApp:myAbcUpdate', function(event, result) {
            vm.myAbc = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
