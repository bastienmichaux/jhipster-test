(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('FoobarmyentityController', FoobarmyentityController);

    FoobarmyentityController.$inject = ['Foobarmyentity'];

    function FoobarmyentityController(Foobarmyentity) {

        var vm = this;

        vm.foobarmyentities = [];

        loadAll();

        function loadAll() {
            Foobarmyentity.query(function(result) {
                vm.foobarmyentities = result;
                vm.searchQuery = null;
            });
        }
    }
})();
