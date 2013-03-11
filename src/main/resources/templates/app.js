var app = angular.module( 'JLog', [ 'ngResource', 'postService' ] );
app.run(function($resource) {
});

angular.module('postService', ['ngResource']).factory('Phone', function($resource){
  return $resource('./blog/posts/:id', {id:'@id'});
});

function PostListController($scope, Post) {
    $scope.posts = Post.query();
    
    $scope.remove = function(item) {
    	console.log('stuff');
    	console.log(item);
    	item.$remove();
    	$scope.posts.splice(item, 1);
    }
};

PostListController.$inject = ['$scope', 'Phone'];
