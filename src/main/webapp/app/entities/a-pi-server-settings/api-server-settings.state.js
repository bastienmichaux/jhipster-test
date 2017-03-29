(function() {
    'use strict';

    angular
        .module('cApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('api-server-settings', {
            parent: 'entity',
            url: '/api-server-settings',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.aPIServerSettings.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/a-pi-server-settings/a-pi-server-settings.html',
                    controller: 'APIServerSettingsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aPIServerSettings');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('api-server-settings-detail', {
            parent: 'api-server-settings',
            url: '/api-server-settings/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.aPIServerSettings.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/a-pi-server-settings/api-server-settings-detail.html',
                    controller: 'APIServerSettingsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('aPIServerSettings');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'APIServerSettings', function($stateParams, APIServerSettings) {
                    return APIServerSettings.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'api-server-settings',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('api-server-settings-detail.edit', {
            parent: 'api-server-settings-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/a-pi-server-settings/api-server-settings-dialog.html',
                    controller: 'APIServerSettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['APIServerSettings', function(APIServerSettings) {
                            return APIServerSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('api-server-settings.new', {
            parent: 'api-server-settings',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/a-pi-server-settings/api-server-settings-dialog.html',
                    controller: 'APIServerSettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                myAPIServerSettings: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('api-server-settings', null, { reload: 'api-server-settings' });
                }, function() {
                    $state.go('api-server-settings');
                });
            }]
        })
        .state('api-server-settings.edit', {
            parent: 'api-server-settings',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/a-pi-server-settings/api-server-settings-dialog.html',
                    controller: 'APIServerSettingsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['APIServerSettings', function(APIServerSettings) {
                            return APIServerSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('api-server-settings', null, { reload: 'api-server-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('api-server-settings.delete', {
            parent: 'api-server-settings',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/a-pi-server-settings/api-server-settings-delete-dialog.html',
                    controller: 'APIServerSettingsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['APIServerSettings', function(APIServerSettings) {
                            return APIServerSettings.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('api-server-settings', null, { reload: 'api-server-settings' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
