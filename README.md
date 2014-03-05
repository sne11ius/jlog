jlog
====

Simple java blog with gplus login.

[![Build Status](http://build.wasis.nu/buildStatus/icon?job=jlog_continuous_deploy)](http://build.wasis.nu/job/jlog_continuous_deploy/)

Stack
=====
 - [mongodb](http://www.mongodb.org/) & [com.github.jmkgreen.morphia](https://github.com/jmkgreen/morphia)
 - [jersey](https://jersey.java.net/)
 - [com.google.apis](https://code.google.com/p/google-api-java-client/wiki/APIs)
 - [rome](https://rometools.jira.com/secure/Dashboard.jspa)
 - (a little) [freemarker](http://freemarker.sourceforge.net/)
 - [angularjs](http://angularjs.org/)
 - [bootstrap](http://twitter.github.io/bootstrap/)
 - [toastr](https://github.com/CodeSeven/toastr)
 - (tiny portions of) [jquery](http://jquery.com/)
 - [flippant.js](https://github.com/mintchaos/flippant.js)
 - [ZeroClipboard](https://github.com/zeroclipboard/zeroclipboard)

Screens
=======
logged in as owner:

![screenshot 1](https://raw.github.com/sne11ius/jlog/master/screenshot001.png)

not logged in & mobile:

![screenshot 2](https://raw.github.com/sne11ius/jlog/master/screenshot002.png)

run
===
1. Try

        mvn compile package && java -jar target/jlog-0.0.1-SNAPSHOT.jar
2. See it fail
3. Add proper `nu.wasis.blog.util.PrivateConstants.java`
4. ...
5. Profit
