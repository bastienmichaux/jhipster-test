(function() {
    'use strict';

    angular
        .module('cApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('foo-bar-api-settings-2', {
            parent: 'entity',
            url: '/foo-bar-api-settings-2',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.fooBarAPISettings2.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/foo-bar-api-settings-2/foo-bar-api-settings-2-s.html',
                    controller: 'FooBarAPISettings2Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fooBarAPISettings2');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('foo-bar-api-settings-2-detail', {
            parent: 'foo-bar-api-settings-2',
            url: '/foo-bar-api-settings-2/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.fooBarAPISettings2.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/foo-bar-api-settings-2/foo-bar-api-settings-2-detail.html',
                    controller: 'FooBarAPISettings2DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fooBarAPISettings2');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FooBarAPISettings2', function($stateParams, FooBarAPISettings2) {
                    return FooBarAPISettings2.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'foo-bar-api-settings-2',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('foo-bar-api-settings-2-detail.edit', {
            parent: 'foo-bar-api-settings-2-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foo-bar-api-settings-2/foo-bar-api-settings-2-dialog.html',
                    controller: 'FooBarAPISettings2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FooBarAPISettings2', function(FooBarAPISettings2) {
                            return FooBarAPISettings2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('foo-bar-api-settings-2.new', {
            parent: 'foo-bar-api-settings-2',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foo-bar-api-settings-2/foo-bar-api-settings-2-dialog.html',
                    controller: 'FooBarAPISettings2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fooBarAPISettingsFieldName: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('foo-bar-api-settings-2', null, { reload: 'foo-bar-api-settings-2' });
                }, function() {
                    $state.go('foo-bar-api-settings-2');
                });
            }]
        })
        .state('foo-bar-api-settings-2.edit', {
            parent: 'foo-bar-api-settings-2',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foo-bar-api-settings-2/foo-bar-api-settings-2-dialog.html',
                    controller: 'FooBarAPISettings2DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FooBarAPISettings2', function(FooBarAPISettings2) {
                            return FooBarAPISettings2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('foo-bar-api-settings-2', null, { reload: 'foo-bar-api-settings-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('foo-bar-api-settings-2.delete', {
            parent: 'foo-bar-api-settings-2',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foo-bar-api-settings-2/foo-bar-api-settings-2-delete-dialog.html',
                    controller: 'FooBarAPISettings2DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FooBarAPISettings2', function(FooBarAPISettings2) {
                            return FooBarAPISettings2.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('foo-bar-api-settings-2', null, { reload: 'foo-bar-api-settings-2' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
