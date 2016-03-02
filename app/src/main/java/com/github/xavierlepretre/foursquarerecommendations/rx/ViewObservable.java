package com.github.xavierlepretre.foursquarerecommendations.rx;

import android.support.annotation.NonNull;
import android.view.View;

import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

public class ViewObservable
{
    @NonNull public static Observable<View> clicked(final View view)
    {
        return Observable.create(new OnSubscribe<View>()
        {
            @Override public void call(final Subscriber<? super View> subscriber)
            {
                View.OnClickListener listener = new View.OnClickListener()
                {
                    @Override public void onClick(View v)
                    {
                        subscriber.onNext(v);
                    }
                };
                view.setOnClickListener(listener);
                subscriber.add(Subscriptions.create(new Action0()
                {
                    @Override public void call()
                    {
                        view.setOnClickListener(null);
                    }
                }));
            }
        });
    }
}
