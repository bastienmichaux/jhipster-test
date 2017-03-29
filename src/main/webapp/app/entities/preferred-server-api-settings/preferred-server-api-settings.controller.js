(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('PreferredServerAPISettingsController', PreferredServerAPISettingsController);

    PreferredServerAPISettingsController.$inject = ['PreferredServerAPISettings'];

    function PreferredServerAPISettingsController(PreferredServerAPISettings) {

        var vm = this;

        vm.preferredServerAPISettings = [];

        loadAll();

        function loadAll() {
            PreferredServerAPISettings.query(function(result) {
                vm.preferredServerAPISettings = result;
                vm.searchQuery = null;
            });
        }
    }
})();
