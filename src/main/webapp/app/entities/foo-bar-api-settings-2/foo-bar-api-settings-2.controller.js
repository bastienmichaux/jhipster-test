(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FooBarAPISettings2Controller', FooBarAPISettings2Controller);

    FooBarAPISettings2Controller.$inject = ['FooBarAPISettings2'];

    function FooBarAPISettings2Controller(FooBarAPISettings2) {

        var vm = this;

        vm.fooBarAPISettings2S = [];

        loadAll();

        function loadAll() {
            FooBarAPISettings2.query(function(result) {
                vm.fooBarAPISettings2S = result;
                vm.searchQuery = null;
            });
        }
    }
})();
