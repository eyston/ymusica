angular.module('ymusica').factory('Albums', ['$http', function($http) {
    return {
        query: function(term) {
            return $http.get('/api/album', { params: { q: term } });
        }
    };
}]);

angular.module('ymusica').factory('Artists', ['$http', function($http) {
    return {
        query: function(term) {
            return $http.get('/api/artist', { params: { q:term } });
        }
    };
}]);