
/*******************************************************************************
 * Create the post list controller.
 */
app.controller('PostListController', function($scope, Post, $http, $timeout, $compile) {
    /* Get all posts from the webservice.
     * 
     * First method - query posts via webservice:
     * $scope.posts = Post.query();
     * 
     * Second method: for single-post view, the data will be inlined in this file:
     * $scope.posts = [{'title':'Post Title','body':'Post body text','comments':[...], etc.}];
     */
    $scope.posts = ${GetPostsArrayCommand?string};
    
    /* Init comment object for new comments */
    $scope.comment = {body: ''};
    
    /* Create new post, save it via webservice and show it in case of success */
    $scope.addPost = function() {
        var post = new Post({title: $scope.postTitle, body: $scope.postBody});
        toastr.info('Submitting post&hellip;<br>' + $scope.postTitle);
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
    
    /* Delete post via webservice and remove it from list in case of success */
    $scope.removePost = function(post) {
        toastr.info('Deleting post&hellip;<br>' + $scope.postBody);
        post.$remove(function() {
            for (var i = 0; i < $scope.posts.length; ++i) {
                if ($scope.posts[i].id == post.id) {
                    $scope.posts.splice(i, 1);
                    toastr.success('Post deleted.');
                    return;
                }
            }
        }, function(error) {
            toastr.error(error, 'Error deleting post');
        });
    };

    $scope.enableEdit = function(post) {
    	if ($scope.editedPost == post) {
    		toastr.info('You are already editing this post, idiot.');
    		return;
    	}
    	$scope.editedPost = post;
    	$scope.originalTitle = post.title;
    	$scope.originalBody = post.body;
    	this.showBodyEditor(post);
    	this.showTitleEditor(post);
    	this.fixFlipperStyles();
    };
    
    $scope.showBodyEditor = function(post) {
    	var bodyElement     = $('#' + post.id + ' p')[0],
		    bodyBackContent = '<div id="body-back-content">'
		    	            +     '<textarea ng-model="editedPost.body" class="body-editor input-block-level"></textarea>'
	                        +     '<div class="editor-buttons"><a href ng-click="saveEdited();" class="btn btn-small btn-success">save</a>'
	                        +     '<a href ng-click="cancelEdit();" class="btn btn-small btn-danger">cancel</a></div>'
	                        + '</div>',
            bodyEditor      = flippant.flip(bodyElement, bodyBackContent);
		$compile($('#body-back-content'))($scope);
		$scope.bodyEditor = bodyEditor;
    };
    
    $scope.showTitleEditor = function(post) {
    	var titleElement     = $('#' + post.id + ' h2')[0],
	    	titleBackContent = '<input id="title-back-content" type="text" ng-model="editedPost.title" class="title-editor input-block-level">',
            titleEditor      = flippant.flip(titleElement, titleBackContent);
    	$compile($('#title-back-content'))($scope);
    	$scope.titleEditor = titleEditor;
    };
    
    $scope.fixFlipperStyles = function() {
    	var $titleParent = $('#title-back-content').parent('.flippant-back'), 
    	    $bodyParent = $('#body-back-content').parent('.flippant-back');
    	$titleParent.css('min-height', 0);
		$bodyParent.css('height', parseInt($bodyParent.css('min-height').replace('px', '')) + 10 + 'px');
		$titleParent.css('width', $bodyParent.width() - 300 + 'px');
    };
    
    $scope.saveEdited = function() {
    	$scope.closeEditors();
    	toastr.info('Updating post&hellip;<br>' + $scope.editedPost.title);
    	$http.put('./posts/' + $scope.editedPost.id, $scope.editedPost).success(function(data) {
    		/* nothing to do here... */
    	}, function(error) {
    		toastr.error(error, 'Error updating post');
    	});
    	$scope.editedPost = undefined;
    };
    
    $scope.cancelEdit = function() {
		$scope.editedPost.title = $scope.originalTitle;
		$scope.editedPost.body = $scope.originalBody;
		$scope.editedPost = undefined;
		$scope.closeEditors();
    };
    
    $scope.closeEditors = function() {
    	$scope.bodyEditor.close();
    	$scope.titleEditor.close();
    };
    
    /* Add new comment to post via webservice and ... show it in case of
     * success ;)
     */
    $scope.addComment = function(post) {
        toastr.info('Submitting comment&hellip;<br>' + post.newcomment.body);
        $http.post('./posts/' + post.id + '/comments', post.newcomment).success(function(comment) {
            post.comments.push(comment);
            post.newcomment = '';
            toastr.success('Comment added.');
        }).error(function(error) {
            toastr.error(error, 'Error adding comment');
        });
    };
    
    /* Remove comment ... */
    $scope.removeComment = function(post, comment) {
        toastr.info('Deleting comment&hellip;<br>' + comment.body);
        $http.delete('./posts/' + post.id + '/comments/' + comment.id).success(function(data) {
            /* TODO: this is kind of ugly */
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
    };
    
    /*
     * Init scope-variables via freemarker, both will be either true or false
     * after rendering.
     * 
     * TODO: remove these two, they should only exist in LoginController
     */
    $scope.isLoggedIn = ${loggedin?string};
    $scope.isOwner = ${isowner?string};
    
    /*
     * Handle the 'loginDone' event: simply set `isLoggedIn' and `isOwner', the
     * view will be updated autmagically.
     */
    $scope.$on('loginDone', function(event, loginInfo) {
        $timeout(function() {
            $scope.isLoggedIn = true;
            $scope.isOwner = loginInfo.isOwner;
        });
        $('#login-loader').hide();
        toastr.success('Login done.');
    });
    
    /*
     * Handle the 'logoutDone' event: simply set `isLoggedIn' and `isOwner', the
     * view will be updated autmagically.
     */
    $scope.$on('logoutDone', function(event) {
        $timeout(function() {
            $scope.isLoggedIn = false;
            $scope.isOwner = false;
        });
    });
});
