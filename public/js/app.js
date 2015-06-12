// app.js

var app = angular.module('marketplaceApp', ['ngResource', 'ngRoute', 'angularUtils.directives.dirPagination']);

app.factory('Ads', function($resource) {
	return $resource('http://localhost:8080/ads/:id')
});

app.config(['$routeProvider', function ($routeProvider) {
	$routeProvider
		.when("/info", {
			templateUrl: "partials/info.html", 
			controller: "mainController"
		})
		.when("/search", {
			templateUrl: "partials/search.html", 
			controller: "mainController"
		})
		.when("/create", {
			templateUrl: "partials/create.html", 
			controller: "mainController"
		})
		.when("/createSuccess/:param", {
			templateUrl: "partials/createSuccess.html", 
			controller: "createAdController"
		})
		.when("/ad/:param", {
			templateUrl: "partials/ad.html", 
			controller: "viewAdController"
		})
		.otherwise({
			redirectTo: '/info'
		});
}]);

app.controller('viewAdController', function controller($scope, $resource, Ads, $routeParams) {
	console.log("viewAdController")
	$scope.param = $routeParams.param;	
	var ad = Ads.get({ id: $scope.param }, function() {
		$scope.ad = ad;
	});
});

app.controller('createAdController', function controller($scope, $routeParams) {
	$scope.param = $routeParams.param; 
});

app.controller('mainController', function controller($scope, $resource, Ads, $location) {

	$scope.DEFAULT_IMAGE = "img/no-photo.jpg";
	$scope.displayResults = false;

	MAX_PRICE = 1000000;

	$scope.getAds = function() {
  	
		$scope.searchOngoing = true;
		$scope.ads = []

		$scope.minFilter = $scope.minPrice
		$scope.maxFilter = $scope.maxPrice
		$scope.textFilter = $scope.textSearch

		if( $scope.minFilter == null || $scope.minFilter == '' ) {
			$scope.minFilter = 0;
		}
		if( $scope.maxFilter == null || $scope.maxFilter == '' ) {
			$scope.maxFilter = MAX_PRICE;
		}
		if( $scope.textFilter == null ) {
			$scope.textFilter = '';
		}

		data = { 
			text: $scope.textFilter,
			minPrice: $scope.minFilter * 100,
			maxPrice: $scope.maxFilter * 100
		};

		console.log("text: " + data.text + ", minPrice: " + data.minPrice + ", maxPrice: " + data.maxPrice)

		var ads = Ads.query(data, function() {
			$scope.ads = ads;
			$scope.numResults = $scope.ads.length;
			$scope.ordering = 'priceCents';
			$scope.searchOngoing = false;	
			$scope.displayResults = true;
			$scope.errorText = '';
		}, function(error, status){
			// console.log('status: ' + error.data.status)
			// console.log('error: ' + error.data.error)
			// console.log('exception: ' + error.data.exception) 
			// console.log('message: ' + error.data.message)
			// console.log('path: ' + error.data.path)
			$scope.searchOngoing = false;
			$scope.errorText = "Error when loading ads. Please wait a moment and try again.";
		});

		
	}

	$scope.saveAd = function() {	

		$scope.errorCreate='';

		if( $scope.createImage == '' ) {
			$scope.createImage = null
		}

		if( $scope.createThumbnail == '' ) {
			$scope.createThumbnail = null
		}

		var dataObj = {
			title: $scope.createTitle, 
			description: $scope.createDescription,
			priceCents: $scope.createPrice * 100,
			imageUrl: $scope.createImage,
			email: $scope.createEmail,
			phone: $scope.createPhone
		};

		Ads.save(dataObj, function(data) {
			$location.path( "/createSuccess/" + data.id);
		}, function(error){
			// console.log('save ad ERROR')
			// console.log('status: ' + error.data.status)
			// console.log('error: ' + error.data.error)
			// console.log('exception: ' + error.data.exception) 
			// console.log('message: ' + error.data.message)
			// console.log('path: ' + error.data.path)
			$scope.handleError(error.data.message);
		}); 
	}

	$scope.handleError = function(msg) {
		if( msg.match(/invalid email/)) {
			$scope.errorCreate="Invalid email address!"
		} else if( msg.match(/invalid price/)) {
			$scope.errorCreate="Price must be between 0 and 1000000!"
		} else if( msg.match(/invalid url/)) {
			$scope.errorCreate="Invalid Image URL!"
		} else {
			$scope.errorCreate="Unexpected error occured."
		}
	}

	$scope.orderByCheap = function() {
		$scope.ordering = 'priceCents';
	}

	$scope.orderByExpensive = function() {
		$scope.ordering = '-priceCents';
	}
	
	$scope.greaterThan = function(prop, val){
		return function(item){
			return item[prop] >= val;
		}
	}

	$scope.lessThan = function(prop, val){
		return function(item){
			return item[prop] <= val;
		}
	}

	$scope.removeCase = function(val) {
		return function(item){
			if( !$scope.textFilter ) {
				val = '';
			}
			return item['title'].toLowerCase().indexOf(val.toLowerCase()) != -1 || item['description'].toLowerCase().indexOf(val.toLowerCase()) != -1;
		}
	}

});





