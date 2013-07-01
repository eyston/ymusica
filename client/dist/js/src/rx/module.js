angular.module('Rx', []).run(function() {
    Rx.promiseToObservable = function(promise) {
        var observable = new Rx.AsyncSubject();

        promise.then(function(res) {
            observable.onNext(res);
            observable.onCompleted();
        }, function(err) {
            observable.onError(err);
            observable.onCompleted();
        });

        return observable;
    };
});