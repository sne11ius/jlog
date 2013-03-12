var app = angular.module( 'JLog', [ 'ngResource', 'postService' ] );
app.run(function($resource) {
});

angular.module('postService', ['ngResource']).factory('Post', function($resource){
    return $resource('./blog/posts/:id', {id:'@id'});
});

function PostListController($scope, Post, $http) {
    $scope.posts = Post.query();
    
    $scope.comment = {body: ''};
    
    $scope.remove = function(item) {
    	item.$remove(function(data) {
    		for (var i = 0; i < $scope.posts.length; ++i) {
    			if ($scope.posts[i].id == item.id) {
    				$scope.posts.splice(i, 1);
    				jQuery.gritter.add({ title: 'Success', text: 'Post removed.' });
    				return
    			}
    		}
    	}, function(error) {
    		jQuery.gritter.add({ title: 'Error', text: error });
    	});
    };
    
    $scope.addPost = function() {
    	var post = new Post({title: $scope.postTitle, body: $scope.postBody});
    	jQuery.gritter.add({ title: 'Submit', text: 'Submitting post...<br>' + $scope.postBody });
    	post.$save(function(data) {
    		$scope.posts.unshift(new Post(data));
    		jQuery.gritter.add({ title: 'Success', text: 'Post added.' });
    	},
    	function(error) {
    		jQuery.gritter.add({ title: 'Error', text: error });
    	});
    	$scope.postTitle = '';
    	$scope.postBody = '';
    };
    
    $scope.addComment = function(post) {
    	jQuery.gritter.add({ title: 'Submit', text: 'Submitting comment...<br>' + post.newcomment.body});
    	$http.post('./blog/posts/' + post.id + '/comments', post.newcomment).success(function(comment) {
    		post.comments.push(comment);
    		post.newcomment = '';
    	}).error(function(error) {
    		jQuery.gritter.add({ title: 'Error', text: error });
    	});
    };
};

PostListController.$inject = ['$scope', 'Post', '$http'];
