(function() {
    'use strict';

    angular
        .module('cApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('my-abc', {
            parent: 'entity',
            url: '/my-abc',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.myAbc.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-abc/my-abcs.html',
                    controller: 'MyAbcController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('myAbc');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('my-abc-detail', {
            parent: 'my-abc',
            url: '/my-abc/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cApp.myAbc.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/my-abc/my-abc-detail.html',
                    controller: 'MyAbcDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('myAbc');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'MyAbc', function($stateParams, MyAbc) {
                    return MyAbc.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'my-abc',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('my-abc-detail.edit', {
            parent: 'my-abc-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-abc/my-abc-dialog.html',
                    controller: 'MyAbcDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyAbc', function(MyAbc) {
                            return MyAbc.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-abc.new', {
            parent: 'my-abc',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-abc/my-abc-dialog.html',
                    controller: 'MyAbcDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                Prefix_MyBook_Suffix: null,
                                Prefix__MyField123_MySuffix: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('my-abc', null, { reload: 'my-abc' });
                }, function() {
                    $state.go('my-abc');
                });
            }]
        })
        .state('my-abc.edit', {
            parent: 'my-abc',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-abc/my-abc-dialog.html',
                    controller: 'MyAbcDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['MyAbc', function(MyAbc) {
                            return MyAbc.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-abc', null, { reload: 'my-abc' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('my-abc.delete', {
            parent: 'my-abc',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/my-abc/my-abc-delete-dialog.html',
                    controller: 'MyAbcDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['MyAbc', function(MyAbc) {
                            return MyAbc.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('my-abc', null, { reload: 'my-abc' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
