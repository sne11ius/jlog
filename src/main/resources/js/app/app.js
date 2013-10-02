/*******************************************************************************
 * Create the angular module `jlog'.
 */
var app = angular.module( 'jlog', ['ngResource', 'postService'] );

/*******************************************************************************
 * Define what we want to do if the app is `run', this could as well be omitted
 * and the jquery call put in global scope...
 */
app.run(function() {
    /* Show ajax loader if user clicks on the gplus login button */
    $('#login-container').click(function() {
        toastr.info('Requesting login...');
        $('#login-loader').show();
    });
    /* Hack to enable prettyPrint */
    window.setInterval(function() {
    	if ('function' === typeof prettyPrint) {
    		prettyPrint();
    	}
    }, 500);
});
