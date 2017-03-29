(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('APISettingsController', APISettingsController);

    APISettingsController.$inject = ['APISettings'];

    function APISettingsController(APISettings) {

        var vm = this;

        vm.aPISettings = [];

        loadAll();

        function loadAll() {
            APISettings.query(function(result) {
                vm.aPISettings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
