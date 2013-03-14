var app = angular.module( 'JLog', [ 'ngResource', 'postService' ] );
app.run(function($resource) {
});

angular.module('postService', ['ngResource']).factory('Post', function($resource){
    return $resource('./blog/posts/:postId', {postId:'@id'});
});

function PostListController($scope, Post, $http) {
    $scope.posts = Post.query();
    
    $scope.comment = {body: ''};
    
    $scope.addPost = function() {
        var post = new Post({title: $scope.postTitle, body: $scope.postBody});
        toastr.info('Submitting post...<br>' + $scope.postBody);
        post.$save(function(data) {
            $scope.posts.unshift(new Post(data));
            toastr.success('Post added.');
        },
        function(error) {
            toastr.error(error, 'Error adding post');
        });
        $scope.postTitle = '';
        $scope.postBody = '';
    };
    
    $scope.removePost = function(item) {
        toastr.info('Deleting post...<br>' + $scope.postBody);
    	item.$remove(function(data) {
    		for (var i = 0; i < $scope.posts.length; ++i) {
    			if ($scope.posts[i].id == item.id) {
    				$scope.posts.splice(i, 1);
    	            toastr.success('Post deleted.');
    				return;
    			}
    		}
    	}, function(error) {
    	    toastr.error(error, 'Error deleting post');
    	});
    };
    
    $scope.addComment = function(post) {
    	toastr.info('Submitting comment...<br>' + post.newcomment.body);
    	$http.post('./blog/posts/' + post.id + '/comments', post.newcomment).success(function(comment) {
    		post.comments.push(comment);
    		post.newcomment = '';
    		toastr.success('Comment added.');
    	}).error(function(error) {
    		toastr.error(error, 'Error adding comment');
    	});
    };
    
    $scope.removeComment = function(post, comment) {
        toastr.info('Deleting comment...<br>' + comment.body);
        $http.delete('./blog/posts/' + post.id + '/comments/' + comment.id).success(function(data) {
            for (var i = 0; i < post.comments.length; ++i) {
                if (post.comments[i].id == comment.id) {
                    post.comments.splice(i, 1);
                    toastr.success('Comment deleted.');
                    return;
                }
            }
        }).error(function(error) {
            toastr.error(error, 'Error deleting comment');
        });
    }
};

PostListController.$inject = ['$scope', 'Post', '$http'];
