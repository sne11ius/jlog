<!doctype html>
<html ng-app="JLog">
<head>
    <meta charset="utf-8">
    <title>wasis.nu/mit/blog</title>
    <script><#include "jq.js"></script>
    <script><#include "gritter.js"></script>
    <script><#include "gplushelper.js"></script>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Vollkorn" />
    <style type="text/css"><#include "styles.css"></style>
    <style type="text/css"><#include "gritter.css"></style>
    <script>
        <#include "angularjs.min.js">
        <#include "angularjs-resource.min.js">
        <#include "app.js">
    </script>
</head>
<body>
    <div class="post" style="margin-bottom:1.9em; margin-top:1.3em;">
        <h1>wasis.nu/mit/blog?</h1>
        <#if loggedin>
            <div style="float:right;position:relative;top:-1.7em;">${nickname} [ <a href="javascript:helper.disconnectServer();" id="disconnect">logout</a> ]</div>
        <#else>
            <div id="gConnect" style="float:right; position:relative; top:-2.1em;">
                <button class="g-signin"
                    data-scope="https://www.googleapis.com/auth/plus.login"
                    data-requestvisibleactions="http://schemas.google.com/CommentActivity"
                    data-clientId="${client_id}"
                    data-accesstype="offline"
                    data-callback="onSignInCallback"
                    data-theme="light"
                    data-width="iconOnly"
                    data-cookiepolicy="single_host_origin"></button>
            </div>
        </#if>
    </div>
    <div ng-controller="PostListController">
        <#if isowner>
            <div class="post">
                <form ng-submit="addPost()">
                    <input type="text" ng-model="postTitle" placeholder="title" style="width:99.5%;"></input>
                    <hr>
                    <textarea ng-model="postBody" placeholder="body" style="width:99.5%;"></textarea>
                    <input class="btn-primary" type="submit" value="submit post">
                </form>
            </div>
        </#if>
        <div class="post" ng-repeat="post in posts">
            <span class="date">{{post.date | date:'yyyy-MM-ddTH:mm:ssZ'}}<#if isowner> <a href="#">{{post.link}}</a> [<a href ng-click="remove(post);">delete</a>]</#if></span>
            <h2>{{post.title}}</h2>
            <hr>
            <p ng-bind-html-unsafe="post.body"></p>
            <hr>
            <#if loggedin>
                <form ng-submit="addComment(post)">
                    <textarea ng-model="post.newcomment.body" rows="2" placeholder="comment" style="width:99.5%;"></textarea>
                    <input class="btn-primary" type="submit" value="submit comment">
                </form>
            </#if>
            <h3 ng-show="post.comments.length != 0">Comments</h3>
            <ol ng-show="post.comments.length != 0">
                <li ng-repeat="comment in post.comments" class="comment">
                    <p>{{comment.body}} <nobr>&mdash; {{comment.author.firstname}} {{comment.author.lastname}} / {{comment.date | date:'yyyy-MM-ddTH:mm:ssZ'}}</nobr></p>
                </li>
            </ol>
        </div>
    </div>
    <div class="date" style="font-size:0.7em">Nat&uuml;rlich in C gehackt, mit dietlibc, libowfat, unter gatling laufend und mit einem tinyldap-Backend. &trade;</div>
</body>
</html>
