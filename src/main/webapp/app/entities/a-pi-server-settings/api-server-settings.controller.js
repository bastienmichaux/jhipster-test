(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('APIServerSettingsController', APIServerSettingsController);

    APIServerSettingsController.$inject = ['APIServerSettings'];

    function APIServerSettingsController(APIServerSettings) {

        var vm = this;

        vm.aPIServerSettings = [];

        loadAll();

        function loadAll() {
            APIServerSettings.query(function(result) {
                vm.aPIServerSettings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
