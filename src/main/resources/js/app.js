/*******************************************************************************
 * Create the angular module `jlog'.
 */
var app = angular.module( 'jlog', [ 'ngResource', 'postService' ] );

/*******************************************************************************
 * Controls the post list ;)
 */
app.controller('PostListController', function($scope, Post, $http) {
    /* Get all posts from the webservice
     * First method: query posts via webservice like so:
     * 
     * $scope.posts = Post.query();
     * 
     * Second method: for single-post view, the data will be inlined in this file like so:
     * 
     * $scope.posts = [{'title':'Post Title','body':'Post body text','comments':[...], etc.}];
     * 
     */
    
    $scope.posts = ${GetPostsArrayCommand?string};
    
    /* Init comment object for new comments */
    $scope.comment = {body: ''};
    
    /* Create new post, save it via webservice and show it in case of success */
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
    
    /* Delete post via webservice and remove it from list in case of success */
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
    
    /* Add new comment to post via webservice and ... show it in case of success ;) */
    $scope.addComment = function(post) {
        toastr.info('Submitting comment...<br>' + post.newcomment.body);
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
        toastr.info('Deleting comment...<br>' + comment.body);
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
     * Init scope-variables via freemarker, both will be either true or false after rendering TODO: remove these two,
     * the should only exist in LoginController
     */
    $scope.isLoggedIn = ${loggedin?string};
    $scope.isOwner = ${isowner?string};
    
    /*
     * Handle the 'loginDone' event: simply set `isLoggedIn' and `isOwner', the view will be updated autmagically.
     */
    $scope.$on('loginDone', function(event, loginInfo) {
        if(!$scope.$$phase) {
            $scope.$apply(function() {
                $scope.isLoggedIn = true;
                $scope.isOwner = loginInfo.isOwner;
            });
        } else {
            $scope.isLoggedIn = true;
            $scope.isOwner = loginInfo.isOwner;
        }
        $('#login-loader').hide();
        toastr.success('Login done.');
    });
    
    /*
     * Handle the 'logoutDone' event: simply set `isLoggedIn' and `isOwner', the view will be updated autmagically.
     */
    $scope.$on('logoutDone', function(event) {
        if(!$scope.$$phase) {
            $scope.$apply(function() {
                $scope.isLoggedIn = false;
                $scope.isOwner = false;
            });
        } else {
            $scope.isLoggedIn = false;
            $scope.isOwner = false;
        }
    });
});

/*******************************************************************************
 * Controls the top login/logout stuff.
 */
app.controller('LoginController', function($scope, $http) {
    /*
     * Init scope-variables via freemarker, both will be either true or false after rendering.
     */
    $scope.isLoggedIn = ${loggedin?string};
    $scope.isOwner = ${isowner?string};
    
    $scope.username = '${username}';
    
    /* Logout and update view, then raise `logoutDone' event */
    $scope.disconnectServer = function() {
        toastr.info('Disconnecting...');
        $http.post('./blog/session/disconnect').success(function() {
            toastr.success('Disconnected.');
            if(!$scope.$$phase) {
                $scope.$apply(function() {
                    $scope.isLoggedIn = false;
                    $scope.isOwner = false;
                });
            } else {
                $scope.isLoggedIn = false;
                $scope.isOwner = false;
            }
            var $rootScope = angular.element(document).scope();
            $rootScope.$broadcast('logoutDone');
        }).error(function(error) {
            toastr.error(error, 'Error during disconnect');
        });
    };

    /*
     * This function will be called from the google api after the user clicks `accepd' or `cancel' in the popup window.
     * The function will also be called if an already logged in user visits the page. TODO: properly implement like in
     * http://stackoverflow.com/a/9857988/649835
     */
    $scope.onSignInCallback = function(data) {
        /* User clicked `cancel' or simply closed the popup. */
        if ('undefined' != typeof data.error) {
            $('#login-loader').hide();
        }
        /*
         * Init the javascript client, try to load user data and promote login to the server
         */
        gapi.client.load('plus', 'v1', function() {
            var request = gapi.client.plus.people.get({
                'userId' : 'me'
            });
            request.execute(function(profile) {
                if ('undefined' != typeof profile.error) {
                    console.log('this is not login you are looking for...');
                    $('#login-loader').hide();
                    return;
                }
                $http({
                    method: 'POST',
                    url: './blog/session/connect?state=${state}&gplus_id=' + profile.id,
                    headers: {'Content-Type': 'application/octet-stream; charset=utf-8'},
                    data: data.code 
                }).success(function(loginInfo) {
                    /* Update the view on success */
                    if(!$scope.$$phase) {
                        $scope.$apply(function() {
                            $scope.isLoggedIn = true;
                            $scope.username = loginInfo.user.firstname + ' ' + loginInfo.user.lastname;
                            $scope.isOwner = loginInfo.isOwner;
                        });
                    } else {
                        $scope.isLoggedIn = true;
                        $scope.username = loginInfo.user.firstname + ' ' + loginInfo.user.lastname;
                        $scope.isOwner = loginInfo.isOwner;
                    }
                    /* Finally raise `loginDone' event */
                    var $rootScope = angular.element(document).scope();
                    $rootScope.$broadcast('loginDone', loginInfo);
                });
            });
        });
    };
    
    /*
     * Get all the login stuff running. $scope.onSignInCallback will be called when google thinks the time is right
     */
    $scope.start = function() {
        gapi.signin.render(
           'g-signin', {
                'scope'                 : 'https://www.googleapis.com/auth/plus.login',
                'requestvisibleactions' : 'http://schemas.google.com/CommentActivity',
                'clientId'              : '${client_id}',
                'accesstype'            : 'offline',
                'callback'              : $scope.onSignInCallback,
                'theme'                 : 'light',
                'width'                 : 'medium',
                'cookiepolicy'          : 'single_host_origin'
            }
        );
    };
    
    $scope.start();
});

/*******************************************************************************
 * Create the angular module `postService' - the webservice for getting/adding/ deleting posts
 */
angular.module('postService', ['ngResource']).factory('Post', function($resource){
    return $resource('./blog/posts/:postId', {postId:'@id'});
});

/*******************************************************************************
 * Define what we want to do if the app is `run', this could as well be omitted and the jquery call put in global
 * scope...
 */
app.run(function() {
    /* Show ajax loader if user clicks on the gplus login button */
    $('#login-container').click(function() {
        toastr.info('Requesting login...');
        $('#login-loader').show();
    });
});
