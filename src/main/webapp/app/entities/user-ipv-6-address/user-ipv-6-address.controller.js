(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('UserIPV6AddressController', UserIPV6AddressController);

    UserIPV6AddressController.$inject = ['UserIPV6Address'];

    function UserIPV6AddressController(UserIPV6Address) {

        var vm = this;

        vm.userIPV6Addresses = [];

        loadAll();

        function loadAll() {
            UserIPV6Address.query(function(result) {
                vm.userIPV6Addresses = result;
                vm.searchQuery = null;
            });
        }
    }
})();
