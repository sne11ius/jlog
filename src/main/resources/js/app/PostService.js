/*******************************************************************************
 * Create the angular module `postService' - the webservice for getting/adding/
 * deleting posts
 */
angular.module('postService', ['ngResource']).factory('Post', function($resource){
    return $resource('./posts/:postId', {postId:'@id'});
});
