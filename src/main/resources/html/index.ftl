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
            <#include "PostListController.js">
            <#include "LoginController.js">
            <#include "PostService.js">
        $COMPRESS_SINGLE_LINE_END
    </script>
    <script src='https://plus.google.com/js/client:plusone.js'>{"parsetags": "explicit"}</script>
    <script>
        <#include "flippant.min.js">
    </script>
    <script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js?skin=sunburst"></script>
    $COMPRESS_SINGLE_LINE_BEGIN
    <style type="text/css" media="screen">
        <#include "flippant.css">
    </style>
    $COMPRESS_SINGLE_LINE_END
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
            <!--img ng-controller="LoginController" ng-click="twitterSignIn()" src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIAAAAAxCAYAAAASqKEbAAAABmJLR0QA/wD/AP+gvaeTAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAB3RJTUUH3QYWFwoUYRrRsAAADuJJREFUeNrtnXuMXNV9xz935s7z7uywY+/DCzZL1rsOsoFg4yZIAVy3oKiQV1uh9o9IFS0tVEUFt9CqaSFqihLSgtomKqmSSFVb0oKqqqGlisKjBFOZNLZrBxbwrm2ol13vrr07npl778zcmbmnf9x5P8+9s15MxJFGex/nnHvO+X3P7/v7/c65dxXK6cYnnrkFuBf4JHA5H6afxrQAvAo8eejAnT8EUMrC/zNfKPJFbfo6AkOb8YUiH4zu2DaLr36f8Zt/4UPRygxXPksheR5j9jh2PvvooQN3/rFy4xPP7PdHYy9etnc/SiDoIKKPpHSpQLZuRfLcsku8+x/fZfqzX+ATasb185Q2GRWX7VY89rXdOCke6gn55MZTABnh5w0rxEquxIXD/0XJSN+mAvdGt+9ECQRQEH2jTIjOIBCSIGjO1+vcuSJ4zx+Tql/pcVFxCyCJ+7LPdFtf0CdfR0QU+XjI5H+IUJjcSfonh+5VgVsDQ6MgWAfx9waB7GAKCZBUj23nmaLuuuKibqXpomj626ku0UOQosNxFWCid3971Sd61FFf3kRFKGEmVYulxCjArSoQV1SV9RO/u9ktqx1EV5QLz9rG7bPbtqPuopDQEKKprl553VJot5HJKX6GfVkUfxhgQG1GERugBYTHznQqK0T5ZzfeFN2E1knIijwQlB6jLVwI1u3zq4adh/HziZrMVTmFuzEg8Goj1JS/aBml+rwy9VcGRnExq5QuetcN+OpP3OZ3pyNqZHnRNMBGgKCS0W7if9pwuuJCIG40QltVLryr8k7P7VTG7gLNbmUaNIAQF5f/vYBACs/lSkWZA7p1o7lOaaAJd2q5bdsV9/2r70s3GpPpczuDtyLzdaGATQGFuAqns2JdbYLeQlI62O1y9OEGBLL83NZGEN2B4JZyGm+6s6YEolxfnxSgKnD7sI/PjvgYCzqNyNrwows2z523mdHFRQdB5botaoagjA4UEjPLq33QkRqU7pKUtvaVbhQgGRQSfVLA9qjCQ1epbA03tibig30JH/sSPr5xpshz52zWI4keUTIhHFzbQnR0z2S0gTQQPLhlDSpd8Vq/0lKPjXDtUQkh+qOA39kWaBD+kgWrBdip1edROZsvcDRtu9ICeKIDIR0MkYk6ypYXPUhJ6QUGxW0wTLTOZMU9INtQgDwAron52KE5jyoIuG8Onltz7l0egq9vh70x5/zuK1Tumcl7MqzcgKCCaNEByN1Ut+Ii6qhIIEN0qFfplrkPLSQkoolNOsSxBIRHG+CagVrw+ZuLNeEDLOTh7lk4+DGI+WEiohD1gVFyb1h1A0HzIFXq6tWPtmVdz5zexl17o1B0B4ObOIOkF9Ap2e1sANmB2FUHgEPp1vurBTimw01x5/yxHcG29ZwyBW9kSvzgfMkzJbRoAOFSkIo3Tu8IBGkPQXR+ngSYvBiBSrMn0KAB6lih1yDUrz7lOjzdqmvxlNZ+vXJKg08N+7l7m+Cv3rV4NVlyTQnNro1wacs0U1HfQJCYt50pQt5wdUNfrWPYOFaqez3SO++gX762uKrw8PYQfzqX59Vk0RsIqoEguX40CL1cJH1uhbxpABCKasSHR6RnlWhbuZA2IHOZNDnTwMplCUc1ooNxAqFwTzgJyusfbkFQxwEtRmA/CzWV9OBpiPbYqLBTg3u2wGR589HvTgR5ZbXgyTi0yxQmTQFN9kfq3Aqpc8vVgc7qOmYmw9hVk65UezuLs5d/n81kWJl/t3au6yRXVhifnCIQCvXUNm5NAEElFNzFC+gXBKeyvfO8bsC/nYfvX+OAIB5Q+PSIyrPLhZ7GYasRWNYAigcKAHJGBhB89ZYdAHznJ+8xlzTIZ02C4Ygrvah0QUe7tucMHYTgrmuvYGpI4+D8Gv95+hxZQ0cNBnsG/jzFcEUfXsB6LhvkbPjLBcd1BNg16Od7SwVpV7GBAcqaTdZ6rq8naxgoCuweHQTg6aAfARRLpUqgpJryWRMzuUbeNCgWLHx+P6GIRmQwjha/zNl6tbZKNpNqoJRgJEohl3XoZXONXvK5LAKYGtK4fnSQo8tpBGCkUxjpFIFQmHyZHirPGtw8TCgSLQeCPMm/dyRwPahAJp2zaseJgNKAMCFplXWKA/QqrjTMiHZeRaNdYaQusLY431C+VCxiZlKYmRTZTBpQMNMXGoFuGOQMo6rizUyGsQmHXoIRjayu8wcvn2ikBl1v+AtQKpYwM2nMTJrRiUlCkSjC51r8bbyADtO6PQjWd+Xw54bqaCFVat+WHmi0ywIU67XOUNlaUDdTzDrh/8rVW7h9coQtAyEyVpFX5tf49vH3yKQcwceCKvfvneCWrQkA/vmtswBMJzS+fXye2aRBLufQS9EuIRD8xrVbmUpovDK/xnOnVnhs30cdOjo+z/17J5ga0tCtIk+/fZan3zpLcnmR0YlJF26g0qQB2riBXlywftIXRuGusdr5oaTVuS2im2vUGN1y47JVjCKlVf4NWiWTPI8AHtg7wR2TI5zV83zr+Dx7xuLcPjnC1JDGQy+fQLeKPLZvB9MJjdk1g1fm17h5a4LphBMnHwj4QTizWQiwslmEgKmExu7RQY4upRCiRkdT+3YwlzQ5q+fZMhDi16/dypGlNHNrBjnTxK9FpbyV+thDixvYy30SnVyeDulLE/Azse55hgMwVmfjzKSLvJkuenK8K4EsW2JDRPsqWmFXVpTYQmBlTfJZk+mExh2TDn/f84M3yFhF/umtRb552y6mExq3Tw6zqOeZTmhkrCIPvnyCjFXk30+d43u/uLsVXBXvpQ34KunL/32So8tpYkGVv7/jWmJBld2jg8yuGeRMnagEAERTKLh1MUi4WEeXQMBkGK7R5DXBTLrI43MGrkN57awaT5HENgxQt8ssbxggYM+oE948spQiYxUJRqJYWZMfzq8xndDYPTYIS054dDZpkLEKBCNRMlmTI8upanlRF7pq/LW27shyyolTWAXmkga7R+PVGSyEQNRZvjI7sUWd1yStAdyyvyYZCDqbs/nWOybPr+RdWe5tjcAuFNAzktjsBpdnyMqZdxoDPvUGqi1A8VU9kMqDKsexgIoQYNuiBqbKn/J5/T6GRiNU7riey2U9NKUeBHjaD7B+RqBZslsEJ1yguTEO0L1loo8eVe4v6vmqMSeAnO68jTQ95KjhE2sGJ1b1ap6poSizawbTCY09Y/FqW+3yr52n0ezNiLaLwmV6QnjYECL6iwPk6kL24Q4uyNtmbUm4K1VoKo9cHeOXxiP8+azOSb3YMVon49cKj9Zqc/cf3391S56/PXaG7765yK+ujbMjofGlT07x7MkVdiQ09m3b5HD9yWUW9TxHllLsGYvz+P6rmV0z2DMWJ2MViQXVhjZXNUCXWEtb2XTQHOsaB+iUFnMlIADAtlD7PF85AyUBe7qAYCLsLBkD7IqrPDit8ZtHLvQM23ZW4cL75lYhl0cIeOTgLL//8Y+wb9sm9m3bVNUMf/Gj0yxkHA3x8ME5PrN9hD1bHME/cnCOT0+NcMNYvCr4bupfCPm22S777KuzHxQvADietPjcuLNQ8YlBeGqlNU+mBH/yrpwb+OUJ8CuwKx5g56DKG6lC57Ct0i0Q1OoFyCY1HMHKmtz0j4d65l3I5HjghTcZHwgzHguRyRc5sWZU7w+GAnxmu+MpHHjxrer137p+WxksOQD8wQA2gkA0Ss7UeaAuL8DNT71WPa4Ym8151HDU9XKwjajaDopMHKA5Hb1QC919KuG4c+cK3ibePyw7WuSecef850dDvJ6ypBdxmjnRq3UyODpOenkRK2t2zBOMRIkNj2KZBnlDZ0E3WagIMxAkGIlStPKkcln2XbmJHWXeP7yU4oaxOOMDIQ6fTbGQyRGMRPGrARCCgcRmEIK8oWNlTYIRx56otCUYiRIf2UJOTzfkiQ2PEghHXBvwvgoIvC4HL2WLvJ0u8NHBAGEfPHwl3HfSuyH47GoNANfFA3IuaVvSFJ4XKtRgkMTWCam8gXAELbGZUsGiVLBQfP7qglH63BKFnMn9L8xw7/VX8rNXbuKGMg08NbPAUzOLztqANtAAVm3TMNqm4e6eVag1j6c9EE2D6GlX8F/PpfmbPQ7/fW4zzGbh6wveADASqB2vWrYnHq94AfZFfL+lmX18ahCfGizzsHNtYPMYpUKRTCbF1147xddeO9VSTzgWJ5rY3JW7Xe02tt1Zvna/ewIBfrxq8Xfv6PzaVQMAPLQVrh+AP3rH2SEsm8I++MNttfNjScvbHje7vTW9nkl23/7glisIaAPkM2kKWRPbLuHz+QlEokQvGyKkxaT3LsqAwa3d4wOEIvp/Newbs2mu0lRuGXEMwluHnN/BFLyYhHTJcQc7pekI3Hd5bUOIXrT5lzOGZw1Q8a/dxhD6AUOnx4RjccKxeNt53U1LKb03DnfVFoqUBqh4IOvwatjv/e8qd30kxm9PDVav3RSvbQh1k7765gX0YqlPkYgWG+FigsEj+3bcFdzvXgvp4krL28Hen/ydU2mOJfP88laN27ZEXZdPWjZfmUny0nLWe8eroeAug7oRmxskZ6NXf6UdmD29GtYaCu6vs4dX8xxezfPI60luGg7zsaEQAwGFqYFAxzKn9SLHknn+dd7ofyZWnYD1ePluY20GN/Bpp9mau6xIboO6KB+IyJcELyyZvLBkrttskR9uF/NqgyjCG1U000UPV1jxQCN1eyc35PsA/RpVckagN9X6Pndd+kMVnewH2/XHuEQ1GrhuFPB+A0H21bBLMQnXgGgs4fOwAla/hf6ifiNo4/jTS0xsI6jp4tOF2ziA0vQkFUhRKsWFz39Jz5BeL0gKsf4aQFwCgFhvCvMhKAKUSgC6CrzkM1OfLw0MfWBUZaedyuIia7JLERBuPw+hUSJZBJ+ZAnheBZ4Mri58PhuOIfzqB5I3q18JFe9PO95PIMi+G6gAcaXANiXHj/UiwdUFgCfVQwfufP7GJ555NDo/88X85q2UIgMIf+ADZkg5orjOn7kk2rORgPBL+rFCgF4scvi8jrn4f/iK1qOHDtz5fLX0jU88s5/a/wsY48P005iWqP2/gJcA/h+9kwwY5ehkxQAAAABJRU5ErkJggg=="-->
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
