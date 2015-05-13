'use strict';

/**
 * @ngdoc overview
 * @name nodeKnxUiApp
 * @description
 * # nodeKnxUiApp
 *
 * Main module of the application.
 */
angular
  .module('nodeKnxUiApp', [
    'ngRoute',
    'ngResource'
  ])
  .config(function ($routeProvider) {
    $routeProvider
      .when('/', {
        templateUrl: 'views/main.html',
        controller: 'MainCtrl',
        controllerAs: 'vm'
      })
      .otherwise({
        redirectTo: '/'
      });
  });
