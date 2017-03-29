(function() {
    'use strict';

    angular
        .module('cApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('api-settings', {
            parent: 'entity',
            url: '/api-settings',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.aPISettings.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/a-pi-settings/a-pi-settings.html',
                    controller: 'APISettingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aPISettings');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('api-settings-detail', {
            parent: 'api-settings',
            url: '/api-settings/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.aPISettings.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/a-pi-settings/api-settings-detail.html',
                    controller: 'APISettingsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aPISettings');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'APISettings', function($stateParams, APISettings) {
                    return APISettings.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'api-settings',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('api-settings-detail.edit', {
            parent: 'api-settings-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/a-pi-settings/api-settings-dialog.html',
                    controller: 'APISettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['APISettings', function(APISettings) {
                            return APISettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('api-settings.new', {
            parent: 'api-settings',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/a-pi-settings/api-settings-dialog.html',
                    controller: 'APISettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                preferredAPI: null,
                                port8080APIListener: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('api-settings', null, { reload: 'api-settings' });
                }, function() {
                    $state.go('api-settings');
                });
            }]
        })
        .state('api-settings.edit', {
            parent: 'api-settings',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/a-pi-settings/api-settings-dialog.html',
                    controller: 'APISettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['APISettings', function(APISettings) {
                            return APISettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('api-settings', null, { reload: 'api-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('api-settings.delete', {
            parent: 'api-settings',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/a-pi-settings/api-settings-delete-dialog.html',
                    controller: 'APISettingsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['APISettings', function(APISettings) {
                            return APISettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('api-settings', null, { reload: 'api-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
