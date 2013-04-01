var app = angular.module( 'jlog', [ 'ngResource', 'postService' ] );

app.controller('PostListController', function($scope, Post, $http) {
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
    };
    
    $scope.isLoggedIn = ${loggedin?string};
    $scope.isOwner = ${isowner?string};
    
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
        $('.login-loader').hide();
        toastr.success('Login done.');
    });
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

app.controller('LoginController', function($scope, $http) {
    $scope.isLoggedIn = ${loggedin?string};
    $scope.isOwner = ${isowner?string};
    $scope.username = '${username}';
    
    $scope.disconnectServer = function() {
        toastr.info('Disconnecting...');
        $http.post(window.location.href + '/session/disconnect').success(function() {
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

    $scope.onSignInCallback = function(data) {
        if ('undefined' != typeof data.error) {
            $('.login-loader').hide();
        }
        gapi.client.load('plus', 'v1', function() {
            var request = gapi.client.plus.people.get({
                'userId' : 'me'
            });
            request.execute(function(profile) {
                if ('undefined' != typeof profile.error) {
                    console.log('this is not login you are looking for...');
                    $('.login-loader').hide();
                    return;
                }
                $.ajax({
                    type : 'POST',
                    url : window.location.href + '/session/connect?state=${state}&gplus_id=' + profile.id,
                    contentType : 'application/octet-stream; charset=utf-8',
                    success : function(loginInfo) {
                        $scope.$apply(function() {
                            $scope.isLoggedIn = true;
                            $scope.username = loginInfo.user.firstname + ' ' + loginInfo.user.lastname;
                            $scope.isOwner = loginInfo.isOwner;
                        });
                        var $rootScope = angular.element(document).scope();
                        $rootScope.$broadcast('loginDone', loginInfo);
                    },
                    data : data.code
                });
            });
        });
    };
    
    $scope.start = function() {
        gapi.signin.render(
           'g-signin', {
                'scope'                 : 'https://www.googleapis.com/auth/plus.login',
                'requestvisibleactions' : 'http://schemas.google.com/CommentActivity',
                'clientId'              : '${client_id}',
                'accesstype'            : 'offline',
                'callback'              : $scope.onSignInCallback,
                'theme'                 : 'light',
                'width'                 : 'iconOnly',
                'cookiepolicy'          : 'single_host_origin'
            }
        );
    };
    
    $scope.start();
});

app.run(function() {
    $('#gConnect').click(function() {
        toastr.info('Requesting login...');
        $('.login-loader').show();
    });
});

angular.module('postService', ['ngResource']).factory('Post', function($resource){
    return $resource('./blog/posts/:postId', {postId:'@id'});
});
