<#compress>
<!doctype html>
        <@compress single_line=true> 
<html ng-app="jlog">
<head>
    <meta charset="utf-8">
    <title>wasis.nu/mit/blog</title>
    <script>
        </@compress>
        <#include "jq.js">
        <#include "toastr.js">
        <#include "angularjs.min.js">
        <#include "angularjs-resource.min.js">
        <@compress single_line=true>
            <#include "app.js">
        </@compress>
    </script>
    <script src='https://plus.google.com/js/client:plusone.js'>{'parsetags': 'explicit'}</script>
    <link href='http://fonts.googleapis.com/css?family=Vollkorn' rel='stylesheet' type='text/css'/>
    <link href='http://fonts.googleapis.com/css?family=Droid+Sans+Mono' rel='stylesheet' type='text/css'>
    <style type="text/css">
        <@compress single_line=true>
            <#include "toastr.css">
            <#include "styles.css">
        </@compress>
    </style>
</head>
<@compress single_line=true>
<body>
    <div class="login-loader">
    </div>
    <div ng-controller="LoginController" class="post" style="margin-bottom:1.9em; margin-top:1.3em;">
        <h1>wasis.nu/mit/blog?</h1>
        <div ng-show="isLoggedIn" style="float:right;position:relative;top:-1.7em;">{{username}} [ <a ng-click="disconnectServer()" href id="disconnect">logout</a> ]</div>
        <div ng-show="!isLoggedIn" id="gConnect" style="float:right; position:relative; top:-2.1em;">
            <button id="g-signin" class="g-signin">
            </button>
        </div>
    </div>
    <div ng-controller="PostListController">
        <div ng-show="isOwner" class="post">
            <form ng-submit="addPost()">
                <input type="text" ng-model="postTitle" placeholder="title" style="width:99.5%;"></input>
                <hr>
                <textarea ng-model="postBody" placeholder="body" style="width:99.5%;"></textarea>
                <input class="btn-primary" type="submit" value="submit post">
            </form>
        </div>
        <div class="post" ng-repeat="post in posts">
            <span class="date">{{post.date | date:'yyyy-MM-ddTH:mm:ssZ'}}<span ng-show="isOwner"> <a href="#">{{post.link}}</a> [<a href ng-click="removePost(post);">delete</a>]</span></span>
            <h2>{{post.title}}</h2>
            <hr>
            <p ng-bind-html-unsafe="post.body"></p>
            <hr>
            <form ng-show="isLoggedIn" ng-submit="addComment(post)">
                <textarea ng-model="post.newcomment.body" rows="2" placeholder="comment" style="width:99.5%;"></textarea>
                <input class="btn-primary" type="submit" value="submit comment">
            </form>
            <h3 ng-show="post.comments.length != 0">Comments</h3>
            <ol ng-show="post.comments.length != 0">
                <li ng-repeat="comment in post.comments" class="comment">
                    <p><span class="comment-body" ng-bind-html-unsafe="comment.body"></span <nobr>&mdash;&nbsp;{{comment.author.firstname}} {{comment.author.lastname}} / {{comment.date | date:'yyyy-MM-ddTH:mm:ssZ'}}<span ng-show="isOwner"> [<a href ng-click="removeComment(post, comment);">delete</a>]</span></nobr></p>
                </li>
            </ol>
        </div>
    </div>
    <div class="date" style="font-size:0.7em">Nat&uuml;rlich in C gehackt, mit <a
                                                href="http://www.fefe.de/dietlibc/">dietlibc</a>, <a
                                                href="http://www.fefe.de/libowfat/">libowfat</a>, unter <a
                                                href="http://www.fefe.de/gatling/">gatling</a> laufend und mit einem <a
                                                href="http://www.fefe.de/tinyldap/">tinyldap-Backend</a>.<a
                                                href="http://blog.refefe.de/faq.html">
                                                &trade;</a><br>
                                                                                            
    </div>
</body>
</@compress>
</html>
</#compress>
