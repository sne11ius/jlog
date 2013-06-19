$COMPRESS_BEGIN
<!DOCTYPE html>
        $COMPRESS_SINGLE_LINE_BEGIN 
<html ng-app="jlog">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>wasis.nu/mit/blog</title>
    <script>
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
        ga('create', 'UA-39708123-1', 'wasis.nu');
        ga('send', 'pageview');
    </script>
    <script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <script>
        $COMPRESS_SINGLE_LINE_END
        <#include "toastr.js">
    </script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.5/angular.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.5/angular-resource.min.js"></script>
    <script>
        <#include "bootstrap.min.js">
        $COMPRESS_SINGLE_LINE_BEGIN
            <#include "app.js">
        $COMPRESS_SINGLE_LINE_END
    </script>
    <script src='https://plus.google.com/js/client:plusone.js'>{"parsetags": "explicit"}</script>
    <script>
        <#include "flippant.min.js">
    </script>
    <style type="text/css" media="screen">
        <#include "flippant.css">
    </style>
    <link href='http://fonts.googleapis.com/css?family=Vollkorn' rel='stylesheet' type='text/css'/>
    <link href='http://fonts.googleapis.com/css?family=Droid+Sans+Mono' rel='stylesheet' type='text/css'>
    <style type="text/css" media="screen">
        <#include "bootstrap.min.css">
        <#include "bootstrap-responsive.min.css">
    </style>
    <style type="text/css">
        $COMPRESS_SINGLE_LINE_BEGIN
            <#include "toastr.css">
            <#include "styles.css">
        $COMPRESS_SINGLE_LINE_END
    </style>
</head>
$COMPRESS_SINGLE_LINE_BEGIN
<body>
    <div id="login-loader"></div>
    <div ng-controller="LoginController" class="navbar navbar-fixed-top site-header">
        <div class="navbar-inner">
            <div class="container">
                <a href="http://wasis.nu/mit/blog"><h1>wasis.nu/mit/blog?</h1></a>
                <span ng-show="isLoggedIn" class="pull-right" id="logout-container">
                    <span
                        id="logout-username"
                    >{{username}}<span
                        class="long-separator"
                    >&mdash;</span></span><a
                        ng-click="disconnectServer()"
                        href
                        id="disconnect"
                        class="btn">Logout</a><a
                        href="http://wasis.nu/mit/blog/feed"><div
                        class="syndication-link"></div></a>
                </span>
                <span ng-show="!isLoggedIn" class="pull-right" id="login-container">
                    <button id="g-signin">
                    </button><a href="http://wasis.nu/mit/blog/feed"><div class="syndication-link"></div></a>
                </span>
            </div>
        </div>
    </div>
    <div ng-controller="PostListController" class="container">
        <div ng-show="isOwner" id="add-post-container" class="well well-small">
            <form ng-submit="addPost()">
                <input type="text" ng-model="postTitle" placeholder="title &ndash; don't forget you can use html here" class="input-block-level"></input>
                <textarea ng-model="postBody" placeholder="body &ndash; don't forget you can use html here, too" class="input-block-level"></textarea>
                <input class="btn pull-right" type="submit" value="create post">
            </form>
        </div>
        <div ng-repeat="post in posts" id="{{post.id}}">
            <span
                class="pull-right">{{post.date | date:'yyyy-MM-ddTHH:mm:ss'}}<span
                class="long-separator">&mdash;</span><a
                href="http://wasis.nu/mit/blog?postId={{post.id}}"
                class="btn btn-mini">link</a><span
                ng-show="isOwner"> <a
                href
                ng-click="enableEdit(post);"
                class="btn btn-mini btn-action">edit</a> <a
                href ng-click="removePost(post);"
                class="btn btn-mini btn-danger">delete</a></span></span>
            <h2 ng-bind-html-unsafe="post.title" class="post-title"></h2>
            <p ng-bind-html-unsafe="post.body" class="post-body"></p>
            <div ng-show="post.comments.length != 0">
                <h3>Comments</h3>
                <ol>
                    <li ng-repeat="comment in post.comments">
                        <p><span
                        ng-bind-html-unsafe="comment.body"></span> <nobr>&mdash;&nbsp;{{comment.author.firstname}} {{comment.author.lastname}} / {{comment.date | date:'yyyy-MM-ddTHH:mm:ss'}}<span
                        ng-show="isOwner"> <a
                        href
                        ng-click="removeComment(post, comment);"
                        class="btn btn-mini btn-danger">delete comment</a></span></nobr></p>
                    </li>
                </ol>
            </div>
            <form ng-show="isLoggedIn" ng-submit="addComment(post)" class="post-comment-container well well-small">
                <textarea ng-model="post.newcomment.body" rows="2" placeholder="comment" class="input-block-level"></textarea>
                <input class="btn pull-right" type="submit" value="submit comment">
            </form>
            <hr>
        </div>
    </div>
    <div class="navbar navbar-fixed-bottom well">
        <div class="container">
            <div class="pull-left github-badge">
                <a href="https://github.com/sne11ius/jlog">
                    <img height="40px" width="40px" src="data:image/gif;base64,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">
                    github/sne11ius/jlog
                </a>
            </div>
            <div class="pull-right fefe-hommage">Nat&uuml;rlich in C gehackt, mit <a
                     href="http://www.fefe.de/dietlibc/">dietlibc</a>, <a
                     href="http://www.fefe.de/libowfat/">libowfat</a>, unter <a
                     href="http://www.fefe.de/gatling/">gatling</a> laufend und mit einem <a
                     href="http://www.fefe.de/tinyldap/">tinyldap-Backend</a>.<a
                     href="http://blog.refefe.de/faq.html">&trade;</a>
            </div>
        </div>
    </div>
</body>
$COMPRESS_SINGLE_LINE_END
</html>
$COMPRESS_END
