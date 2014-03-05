/*******************************************************************************
 * Controls the top login/logout stuff.
 */
app.controller('LoginController', function($scope, $http, $timeout) {
    /*
     * Init scope-variables via freemarker, both will be either true or false
     * after rendering.
     */
    $scope.isLoggedIn = ${loggedin?string};
    $scope.isOwner = ${isowner?string};
    
    $scope.username = '${username}';
    
    /* Logout and update view, then raise `logoutDone' event */
    $scope.disconnectServer = function() {
        toastr.info('Disconnecting...');
        $http.post('./blog/session/gplus/logout').success(function() {
            toastr.success('Disconnected.');
            $timeout(function() {
                $scope.isLoggedIn = false;
                $scope.isOwner = false;
            });
            angular.element(document).scope().$broadcast('logoutDone');
        }).error(function(error) {
            toastr.error(error, 'Error during disconnect');
        });
    };

    /*
     * This function will be called from the google api after the user clicks
     * `accepd' or `cancel' in the popup window.
     * The function will also be called if an already logged in user visits the
     * page.
     * 
     * TODO: properly implement like in http://stackoverflow.com/a/9857988/649835
     */
    $scope.onSignInCallback = function(data) {
        /* User clicked `cancel' or simply closed the popup. */
        if ('undefined' != typeof data.error) {
            $('#login-loader').hide();
        }
        /*
         * Init the javascript client, try to load user data and promote login
         * to the server
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
                    url: './blog/session/gplus/login?gplus_id=' + profile.id,
                    headers: {'Content-Type': 'application/octet-stream; charset=utf-8'},
                    data: data.code 
                }).success(function(loginInfo) {
                    /* Update the view on success */
                    $timeout(function() {
                        $scope.isLoggedIn = true;
                        $scope.username = loginInfo.user.firstname + ' ' + loginInfo.user.lastname;
                        $scope.isOwner = loginInfo.isOwner;
                    });
                    /* Finally raise `loginDone' event */
                    angular.element(document).scope().$broadcast('loginDone', loginInfo);
                });
            });
        });
    };
    
    /*
     * Get all the login stuff running. $scope.onSignInCallback will be called
     * when google thinks the time is right
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
    
    $scope.twitterSignIn = function() {
    	console.log('twitter sign in requested...');
    	$http({
            method: 'GET',
            url: './blog/session/twitter/login'
        }).success(function(authenticationURL) {
        	console.log('authenticationURL:');
        	console.log(authenticationURL);
        	window.open(authenticationURL, 'twitterSignInPopup', 'status=0,toolbar=0');
        }).error(function(error) {
        	console.log('error:');
        	console.log(error);
        });
    };
    
    $scope.start();
});
