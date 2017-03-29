(function() {
    'use strict';

    angular
        .module('cApp')
        .controller('MyAbcController', MyAbcController);

    MyAbcController.$inject = ['MyAbc'];

    function MyAbcController(MyAbc) {

        var vm = this;

        vm.myAbcs = [];

        loadAll();

        function loadAll() {
            MyAbc.query(function(result) {
                vm.myAbcs = result;
                vm.searchQuery = null;
            });
        }
    }
})();
