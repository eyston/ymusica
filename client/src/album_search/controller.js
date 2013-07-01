angular.module('ymusica').controller('AlbumSearch', ['$scope', 'Albums', 'Artists', '$q', function($scope, albums, artists, $q) {

    $scope.albums = [];
    $scope.artists = [];

    var watchToObservable = function(scope, expression) {
        var observable = new Rx.Subject();

        scope.$watch(expression, observable.onNext.bind(observable));

        return observable;
    }

    var functionToObservable = function(scope, name) {

        var observable = new Rx.Subject();

        scope[name] = function(value) {
            observable.onNext(value);
        };

        return observable;
    }

    var terms = functionToObservable($scope, 'searchMusic');

    terms.sample(250)
        .select(function(term) {
            var promise = $q.all([albums.query(term), artists.query(term)]);
            return Rx.promiseToObservable(promise)
        })
        .switchLatest()
        .select(function(promise) { return [promise[0].data.albums, promise[1].data.artists]; })
        .subscribe(function(result) {
            $scope.albums = result[0].slice(0, 5);
            $scope.artists = result[1].slice(0, 5);
            $scope.music = $scope.albums.concat($scope.artists);
        });

    $scope.selectMusic = function(item) {
        console.log('music selected!', item);
        $scope.term = item.name;
    };

    $scope.imageSource = function(item) {
        return item.images['medium'];
    };

    $scope.hasAlbums = function() {
        return $scope.albums.length > 0;
    };

    $scope.hasArtists = function() {
        return $scope.artists.length > 0;
    };

}]);