<!doctype html>
<html ng-app="JLog">
<head>
    <meta charset="utf-8">
    <title>wasis.nu/mit/blog</title>
    <script><#include "jq.js"></script>
    <script><#include "gplushelper.js"></script>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Vollkorn" />
    <style type="text/css"><#include "styles.css"></style>
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
        #posts: {{posts.length}}
        <div class="post" ng-repeat="post in posts">
            <span class="date">{{post.date | date:'yyyy-MM-ddTH:mm:ssZ'}} <a href="#">{{post.link}}</a> [<a href ng-click="remove(post);">delete</a>]</span>
            <h2>{{post.title}}</h2>
            <hr>
            <p>{{post.body}}</p>
            <hr>
            <h3>Comments</h3>
            <ol>
                <li class="comment"><p>This is a comment for the post. <nobr>&mdash; Firstname Lastname / 2013-05-04T01:02:03:04</nobr></p></li>
            </ol>
        </div>
    </div>
</body>
</html>
