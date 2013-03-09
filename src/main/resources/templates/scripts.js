(function() {
    var po = document.createElement('script');
    po.type = 'text/javascript';
    po.async = true;
    po.src = 'https://plus.google.com/js/client:plusone.js';
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(po, s);
})();

function addComment(id) {
    var body = $('#' + id).find('.commentbody').val();
    if (body == '') {
        return;
    }
    /*
    $.post(window.location.href + '/posts/' + id + '/comments',
        JSON.stringify({body:body}),
        function(response) {
            console.log('response: ' + response);
        }
    );
    */
    var body = $('#' + id).find('.commentbody').val();
    if (body == '') {
        return;
    }
    $.ajax(window.location.href + '/posts/' + id + '/comments', {
    	type: 'POST',
        contentType : 'application/json',
        data: JSON.stringify({body:body}),
        success: function(data) {
        	console.log('success: ' + data)
        }
    });
    console.log(body);
}

$.get(window.location.href + '/posts/count', function(numPosts) {
    for (var i = 0; i < numPosts; ++i) {
        $.get(window.location.href + '/posts/byindex/' + i, function(post) {
            $('body').append(
                    '<div class="post" id="' + post.id + '">' +
                        '<div class="date">' + post.author + ', ' + new Date(post.date).toISOString() + '</div>' +
                        '<div class="title">' +
                            '<h2>' + post.title + '</h2>' +
                            '<hr>' +
                        '</div>' +
                        '<div class="body">' + post.body + '</div>' +
                        <#if loggedin>
                        '<hr>' +
                        '<div class="addcomment">' +
                            '<textarea class="commentbody" rows="1" placeholder="add comment" style="width:99%"></textarea>' +
                            '<br>' +
                            '<input type="button" value="submit" class="submitcomment" onclick="addComment(\'' + post.id + '\')"></input>' +
                        '</div>' +
                        </#if>
                    '</div>');
        });
    }
});
