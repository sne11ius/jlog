$COMPRESS_BEGIN
<!DOCTYPE html>
        $COMPRESS_SINGLE_LINE_BEGIN 
<html ng-app="jlog">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
    <title>wasis.nu/mit/blog</title>
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
    <div ng-controller="LoginController" class="navbar navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container">
                <h1>wasis.nu/mit/blog?</h1>
                <span ng-show="isLoggedIn" class="pull-right" id="logout-container">
                    <span id="logout-username">
                        {{username}}
                        <span id="logout-seperator">
                            &mdash;
                        </span>
                    </span>
                    <a ng-click="disconnectServer()" href id="disconnect" class="btn">
                        Logout
                    </a>
                </span>
                <span ng-show="!isLoggedIn" class="pull-right" id="login-container">
                    <button id="g-signin">
                    </button>
                </span>
            </div>
        </div>
    </div>
    <div ng-controller="PostListController" class="container">
        <div ng-show="isOwner">
            <form ng-submit="addPost()">
                <input type="text" ng-model="postTitle" placeholder="title"></input>
                <hr>
                <textarea ng-model="postBody" placeholder="body"></textarea>
                <input class="btn-primary" type="submit" value="submit post">
            </form>
        </div>
        <div ng-repeat="post in posts">
            <span>{{post.date | date:'yyyy-MM-ddTH:mm:ssZ'}}<span ng-show="isOwner"> <a href="#">{{post.link}}</a> [<a href ng-click="removePost(post);">delete</a>]</span></span>
            <h2 ng-bind-html-unsafe="post.title"></h2>
            <hr>
            <p ng-bind-html-unsafe="post.body"></p>
            <hr>
            <form ng-show="isLoggedIn" ng-submit="addComment(post)">
                <textarea ng-model="post.newcomment.body" rows="2" placeholder="comment"></textarea>
                <input class="btn-primary" type="submit" value="submit comment">
            </form>
            <h3 ng-show="post.comments.length != 0">Comments</h3>
            <ol ng-show="post.comments.length != 0">
                <li ng-repeat="comment in post.comments">
                    <p><span ng-bind-html-unsafe="comment.body"></span> <nobr>&mdash;&nbsp;{{comment.author.firstname}} {{comment.author.lastname}} / {{comment.date | date:'yyyy-MM-ddTH:mm:ssZ'}}<span ng-show="isOwner"> [<a href ng-click="removeComment(post, comment);">delete</a>]</span></nobr></p>
                </li>
            </ol>
        </div>
    </div>
    <div>Nat&uuml;rlich in C gehackt, mit <a
         href="http://www.fefe.de/dietlibc/">dietlibc</a>, <a
         href="http://www.fefe.de/libowfat/">libowfat</a>, unter <a
         href="http://www.fefe.de/gatling/">gatling</a> laufend und mit einem <a
         href="http://www.fefe.de/tinyldap/">tinyldap-Backend</a>.<a
         href="http://blog.refefe.de/faq.html">
         &trade;</a><br>
                                                                                            
    </div>
</body>
$COMPRESS_SINGLE_LINE_END
</html>
$COMPRESS_END
