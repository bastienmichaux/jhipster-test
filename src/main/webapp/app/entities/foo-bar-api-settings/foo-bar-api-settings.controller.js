(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FooBarAPISettingsController', FooBarAPISettingsController);

    FooBarAPISettingsController.$inject = ['FooBarAPISettings'];

    function FooBarAPISettingsController(FooBarAPISettings) {

        var vm = this;

        vm.fooBarAPISettings = [];

        loadAll();

        function loadAll() {
            FooBarAPISettings.query(function(result) {
                vm.fooBarAPISettings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
