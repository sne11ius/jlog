Blog
====

Simple java blog with gplus login.

Stack
=====
 - mongodb & com.github.jmkgreen.morphia
 - jersey
 - com.google.apis
 - (a little) freemarker
 - angularjs

Screens
=======
logged in as owner:

![screenshot 1](https://raw.github.com/sne11ius/jlog/master/screenshot001.png)

not logged in:

![screenshot 2](https://raw.github.com/sne11ius/jlog/master/screenshot002.png)

run
===
1. Try

        mvn compile package && java -jar target/jlog-0.0.1-SNAPSHOT.jar
2. See it fail
3. Add proper `nu.wasis.blog.util.PrivateConstants.java`
4. ...
5. Profit
