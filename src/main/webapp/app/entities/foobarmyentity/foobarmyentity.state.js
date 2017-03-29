(function() {
    'use strict';

    angular
        .module('cApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('foobarmyentity', {
            parent: 'entity',
            url: '/foobarmyentity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.foobarmyentity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/foobarmyentity/foobarmyentities.html',
                    controller: 'FoobarmyentityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('foobarmyentity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('foobarmyentity-detail', {
            parent: 'foobarmyentity',
            url: '/foobarmyentity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.foobarmyentity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/foobarmyentity/foobarmyentity-detail.html',
                    controller: 'FoobarmyentityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('foobarmyentity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Foobarmyentity', function($stateParams, Foobarmyentity) {
                    return Foobarmyentity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'foobarmyentity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('foobarmyentity-detail.edit', {
            parent: 'foobarmyentity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foobarmyentity/foobarmyentity-dialog.html',
                    controller: 'FoobarmyentityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Foobarmyentity', function(Foobarmyentity) {
                            return Foobarmyentity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('foobarmyentity.new', {
            parent: 'foobarmyentity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foobarmyentity/foobarmyentity-dialog.html',
                    controller: 'FoobarmyentityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                mYFoobarfield: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('foobarmyentity', null, { reload: 'foobarmyentity' });
                }, function() {
                    $state.go('foobarmyentity');
                });
            }]
        })
        .state('foobarmyentity.edit', {
            parent: 'foobarmyentity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foobarmyentity/foobarmyentity-dialog.html',
                    controller: 'FoobarmyentityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Foobarmyentity', function(Foobarmyentity) {
                            return Foobarmyentity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('foobarmyentity', null, { reload: 'foobarmyentity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('foobarmyentity.delete', {
            parent: 'foobarmyentity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/foobarmyentity/foobarmyentity-delete-dialog.html',
                    controller: 'FoobarmyentityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Foobarmyentity', function(Foobarmyentity) {
                            return Foobarmyentity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('foobarmyentity', null, { reload: 'foobarmyentity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
