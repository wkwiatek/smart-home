'use strict';

/**
 * @ngdoc function
 * @name nodeKnxUiApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the nodeKnxUiApp
 */
angular.module('nodeKnxUiApp')
  .controller('MainCtrl', function ($scope, $resource) {
    var vm = this;
    vm.events = $resource('http://localhost:3000/events.json').query();
  });
