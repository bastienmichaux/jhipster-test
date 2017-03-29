(function() {
    'use strict';

    angular
        .module('cApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('preferred-server-api-settings', {
            parent: 'entity',
            url: '/preferred-server-api-settings',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.preferredServerAPISettings.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/preferred-server-api-settings/preferred-server-api-settings.html',
                    controller: 'PreferredServerAPISettingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('preferredServerAPISettings');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('preferred-server-api-settings-detail', {
            parent: 'preferred-server-api-settings',
            url: '/preferred-server-api-settings/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.preferredServerAPISettings.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/preferred-server-api-settings/preferred-server-api-settings-detail.html',
                    controller: 'PreferredServerAPISettingsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('preferredServerAPISettings');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PreferredServerAPISettings', function($stateParams, PreferredServerAPISettings) {
                    return PreferredServerAPISettings.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'preferred-server-api-settings',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('preferred-server-api-settings-detail.edit', {
            parent: 'preferred-server-api-settings-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/preferred-server-api-settings/preferred-server-api-settings-dialog.html',
                    controller: 'PreferredServerAPISettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PreferredServerAPISettings', function(PreferredServerAPISettings) {
                            return PreferredServerAPISettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('preferred-server-api-settings.new', {
            parent: 'preferred-server-api-settings',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/preferred-server-api-settings/preferred-server-api-settings-dialog.html',
                    controller: 'PreferredServerAPISettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                preferredServerAPISettingsHTTPPort: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('preferred-server-api-settings', null, { reload: 'preferred-server-api-settings' });
                }, function() {
                    $state.go('preferred-server-api-settings');
                });
            }]
        })
        .state('preferred-server-api-settings.edit', {
            parent: 'preferred-server-api-settings',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/preferred-server-api-settings/preferred-server-api-settings-dialog.html',
                    controller: 'PreferredServerAPISettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PreferredServerAPISettings', function(PreferredServerAPISettings) {
                            return PreferredServerAPISettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('preferred-server-api-settings', null, { reload: 'preferred-server-api-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('preferred-server-api-settings.delete', {
            parent: 'preferred-server-api-settings',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/preferred-server-api-settings/preferred-server-api-settings-delete-dialog.html',
                    controller: 'PreferredServerAPISettingsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PreferredServerAPISettings', function(PreferredServerAPISettings) {
                            return PreferredServerAPISettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('preferred-server-api-settings', null, { reload: 'preferred-server-api-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
