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
    	jQuery('pre').each(
			function(idx, val) {
				var clip = new ZeroClipboard(this);
				var that = this;
				jQuery(that).tooltip({
					title: 'Click this element to copy to clipboard.',
					container: 'body',
					trigger: 'manual'
				});
				clip.on('dataRequested', function(client, args) {
					var text = jQuery(this).text();
					client.setText(text);
				});
				clip.on('mouseover', function(client, args) {
					jQuery(this).tooltip('show');
				});
				clip.on('mouseout', function(client, args) {
					jQuery(this).tooltip('toggle');
				});
				clip.on('complete', function(client, args) {
					toastr.success('Code copied for your pleasure&hellip;');
				});
			}
    	);
    }, 500);
});
