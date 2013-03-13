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
        jQuery.gritter.add({ title: 'Submit', text: 'Submitting post...<br>' + $scope.postBody });
        post.$save(function(data) {
            $scope.posts.unshift(new Post(data));
            jQuery.gritter.add({ title: 'Success', text: 'Post added.' });
        },
        function(error) {
            jQuery.gritter.add({ title: 'Error adding post', text: error });
        });
        $scope.postTitle = '';
        $scope.postBody = '';
    };
    
    $scope.removePost = function(item) {
        jQuery.gritter.add({ title: 'Delete', text: 'Deleting post...<br>' + $scope.postBody });
    	item.$remove(function(data) {
    		for (var i = 0; i < $scope.posts.length; ++i) {
    			if ($scope.posts[i].id == item.id) {
    				$scope.posts.splice(i, 1);
    				jQuery.gritter.add({ title: 'Success', text: 'Post deleted.' });
    				return;
    			}
    		}
    	}, function(error) {
    		jQuery.gritter.add({ title: 'Error deleting post', text: error });
    	});
    };
    
    $scope.addComment = function(post) {
    	jQuery.gritter.add({ title: 'Submit', text: 'Submitting comment...<br>' + post.newcomment.body});
    	$http.post('./blog/posts/' + post.id + '/comments', post.newcomment).success(function(comment) {
    		post.comments.push(comment);
    		post.newcomment = '';
    	}).error(function(error) {
    		jQuery.gritter.add({ title: 'Error adding comment', text: error });
    	});
    };
    
    $scope.removeComment = function(post, comment) {
        jQuery.gritter.add({ title: 'Delete', text: 'Deleting comment...<br>' + comment.body});
        $http.delete('./blog/posts/' + post.id + '/comments/' + comment.id).success(function(data) {
            for (var i = 0; i < post.comments.length; ++i) {
                if (post.comments[i].id == comment.id) {
                    post.comments.splice(i, 1);
                    jQuery.gritter.add({ title: 'Success', text: 'Comment deleted.' });
                    return;
                }
            }
        }).error(function(error) {
            jQuery.gritter.add({ title: 'Error deleting comment', text: error });
        });
    }
};

PostListController.$inject = ['$scope', 'Post', '$http'];
