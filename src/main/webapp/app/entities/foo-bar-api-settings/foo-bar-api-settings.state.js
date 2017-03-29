(function() {
    'use strict';

    angular
        .module('cApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('foo-bar-api-settings', {
            parent: 'entity',
            url: '/foo-bar-api-settings',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.fooBarAPISettings.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/foo-bar-api-settings/foo-bar-api-settings.html',
                    controller: 'FooBarAPISettingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fooBarAPISettings');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('foo-bar-api-settings-detail', {
            parent: 'foo-bar-api-settings',
            url: '/foo-bar-api-settings/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.fooBarAPISettings.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/foo-bar-api-settings/foo-bar-api-settings-detail.html',
                    controller: 'FooBarAPISettingsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('fooBarAPISettings');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'FooBarAPISettings', function($stateParams, FooBarAPISettings) {
                    return FooBarAPISettings.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'foo-bar-api-settings',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('foo-bar-api-settings-detail.edit', {
            parent: 'foo-bar-api-settings-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foo-bar-api-settings/foo-bar-api-settings-dialog.html',
                    controller: 'FooBarAPISettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FooBarAPISettings', function(FooBarAPISettings) {
                            return FooBarAPISettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('foo-bar-api-settings.new', {
            parent: 'foo-bar-api-settings',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foo-bar-api-settings/foo-bar-api-settings-dialog.html',
                    controller: 'FooBarAPISettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                fooBarAPISettingsField: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('foo-bar-api-settings', null, { reload: 'foo-bar-api-settings' });
                }, function() {
                    $state.go('foo-bar-api-settings');
                });
            }]
        })
        .state('foo-bar-api-settings.edit', {
            parent: 'foo-bar-api-settings',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foo-bar-api-settings/foo-bar-api-settings-dialog.html',
                    controller: 'FooBarAPISettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FooBarAPISettings', function(FooBarAPISettings) {
                            return FooBarAPISettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('foo-bar-api-settings', null, { reload: 'foo-bar-api-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('foo-bar-api-settings.delete', {
            parent: 'foo-bar-api-settings',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foo-bar-api-settings/foo-bar-api-settings-delete-dialog.html',
                    controller: 'FooBarAPISettingsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FooBarAPISettings', function(FooBarAPISettings) {
                            return FooBarAPISettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('foo-bar-api-settings', null, { reload: 'foo-bar-api-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
