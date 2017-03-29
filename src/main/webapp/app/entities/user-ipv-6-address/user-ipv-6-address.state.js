(function() {
    'use strict';

    angular
        .module('cApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('user-ipv-6-address', {
            parent: 'entity',
            url: '/user-ipv-6-address',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.userIPV6Address.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-ipv-6-address/user-ipv-6-addresses.html',
                    controller: 'UserIPV6AddressController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userIPV6Address');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('user-ipv-6-address-detail', {
            parent: 'user-ipv-6-address',
            url: '/user-ipv-6-address/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.userIPV6Address.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/user-ipv-6-address/user-ipv-6-address-detail.html',
                    controller: 'UserIPV6AddressDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('userIPV6Address');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'UserIPV6Address', function($stateParams, UserIPV6Address) {
                    return UserIPV6Address.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'user-ipv-6-address',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('user-ipv-6-address-detail.edit', {
            parent: 'user-ipv-6-address-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-ipv-6-address/user-ipv-6-address-dialog.html',
                    controller: 'UserIPV6AddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserIPV6Address', function(UserIPV6Address) {
                            return UserIPV6Address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-ipv-6-address.new', {
            parent: 'user-ipv-6-address',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-ipv-6-address/user-ipv-6-address-dialog.html',
                    controller: 'UserIPV6AddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                addressIPV6: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('user-ipv-6-address', null, { reload: 'user-ipv-6-address' });
                }, function() {
                    $state.go('user-ipv-6-address');
                });
            }]
        })
        .state('user-ipv-6-address.edit', {
            parent: 'user-ipv-6-address',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-ipv-6-address/user-ipv-6-address-dialog.html',
                    controller: 'UserIPV6AddressDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['UserIPV6Address', function(UserIPV6Address) {
                            return UserIPV6Address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-ipv-6-address', null, { reload: 'user-ipv-6-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('user-ipv-6-address.delete', {
            parent: 'user-ipv-6-address',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/user-ipv-6-address/user-ipv-6-address-delete-dialog.html',
                    controller: 'UserIPV6AddressDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['UserIPV6Address', function(UserIPV6Address) {
                            return UserIPV6Address.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('user-ipv-6-address', null, { reload: 'user-ipv-6-address' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
