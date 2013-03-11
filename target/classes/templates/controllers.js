function PostListController($scope, $resource) {
  var Post = $resource('/posts/:id', {id: 'id'});
  $scope.posts = Post.query();
}
