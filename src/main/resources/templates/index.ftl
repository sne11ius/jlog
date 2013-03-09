<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>wasis.nu/mit/blog</title>
    <script><#include "jq.js"></script>
    <script><#include "gplushelper.js"></script>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Vollkorn" />
    <style type="text/css">
        * {
            font-family: 'Vollkorn', serif;
        }
        h1 {
            font-size: 1.7em;
            line-height: 0.0001em;
        }
        h2 {
            font-size: 1.2em;
            line-height: 0.0001em;
        }
        a {
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .post {
            margin: 0.5em;
            border: 1px solid black;
            background-color: #ddd;
            border-radius: 0.5em;
            padding: 0.2em 0.4em 0.2em 0.4em;
        }
        hr {
           width: 100%;
           height: 1px;
           border-width: 0px;
           border-top-width: 1px;
        }
        .date {
            float: right;
        }
    </style>
    <script><#include "scripts.js"></script>
</head>
<body>
    <div class="post" style="margin-bottom:1.9em; margin-top:1.3em;">
        <h1>wasis.nu/mit/blog?</h1>
        <#if loggedin>
            <div style="float:right;position:relative;top:-1.7em;">${nickname} <a href="javascript:helper.disconnectServer();" id="disconnect">[logout]</a></div>
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
    <#if isowner>
        <div class="post">
            <div class="title"><input type="text" placeholder="Titel" style="width:99%"></input>
        </div>
        <hr>
        <div class="body">
            <textarea placeholder="Text" style="width:99%" rows="3"></textarea>
        </div>
        <input type="button" value="submit"></input>
    </#if>
</body>
</html>
