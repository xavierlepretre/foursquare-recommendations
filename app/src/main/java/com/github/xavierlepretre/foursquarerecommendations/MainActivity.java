package com.github.xavierlepretre.foursquarerecommendations;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.foursquare.credentials.Keys;
import com.foursquare.model.ExploreResponse;
import com.foursquare.model.RecommendedVenuesGroup;
import com.foursquare.service.FoursquareService;
import com.github.xavierlepretre.foursquarerecommendations.rx.ViewObservable;
import com.github.xavierlepretre.rxdialog.AlertDialogButtonEvent;
import com.github.xavierlepretre.rxdialog.AlertDialogEvent;
import com.github.xavierlepretre.rxdialog.support.RxAlertDialogSupport;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private FloatingActionButton fab;
    private FoursquareService foursquareService;
    private Subscription placeSubscription;

    @VisibleForTesting String desiredPlace;
    @VisibleForTesting List<RecommendedVenuesGroup> recommendedVenuesGroups;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        foursquareService = new FoursquareService(Keys.create(Constants.CLIENT_ID, Constants.CLIENT_SECRET));
        placeSubscription = getDesiredPlaceName(fab)
                .flatMap(new Func1<String, Observable<List<RecommendedVenuesGroup>>>()
                {
                    @Override
                    public Observable<List<RecommendedVenuesGroup>> call(String desiredPlace)
                    {
                        MainActivity.this.desiredPlace = desiredPlace;
                        return getRecommendedVenuesNear(desiredPlace);
                    }
                })
                .doOnNext(new Action1<List<RecommendedVenuesGroup>>()
                {
                    @Override public void call(List<RecommendedVenuesGroup> recommendedVenuesGroups)
                    {
                        MainActivity.this.recommendedVenuesGroups = recommendedVenuesGroups;
                    }
                })
                .subscribe(
                        new Action1<List<RecommendedVenuesGroup>>()
                        {
                            @Override public void call(List<RecommendedVenuesGroup> desiredPlace)
                            {

                            }
                        },
                        new Action1<Throwable>()
                        {
                            @Override public void call(Throwable throwable)
                            {
                                Log.e(TAG, "Failed to listen to place names", throwable);
                            }
                        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override protected void onDestroy()
    {
        placeSubscription.unsubscribe();
        fab = null;
        super.onDestroy();
    }

    @Override public void onMapReady(GoogleMap googleMap)
    {
    }

    @NonNull private Observable<String> getDesiredPlaceName(@NonNull FloatingActionButton fab)
    {
        return ViewObservable.clicked(fab)
                .flatMap(new Func1<View, Observable<String>>()
                {
                    @Override public Observable<String> call(View view)
                    {
                        final EditText input = (EditText) LayoutInflater.from(MainActivity.this)
                                .inflate(R.layout.place_input, null);
                        input.setText(desiredPlace);

                        return new RxAlertDialogSupport.Builder(MainActivity.this)
                                .title(R.string.dialog_place_title)
                                .view(input)
                                .positiveButton(android.R.string.ok)
                                .neutralButton(android.R.string.cancel)
                                .show()
                                .filter(new Func1<AlertDialogEvent, Boolean>()
                                {
                                    @Override public Boolean call(AlertDialogEvent alertDialogEvent)
                                    {
                                        return alertDialogEvent instanceof AlertDialogButtonEvent
                                                && ((AlertDialogButtonEvent) alertDialogEvent).getWhich() == DialogInterface.BUTTON_POSITIVE;
                                    }
                                })
                                .map(new Func1<AlertDialogEvent, String>()
                                {
                                    @Override public String call(AlertDialogEvent alertDialogEvent)
                                    {
                                        return input.getText().toString();
                                    }
                                });
                    }
                });
    }

    @NonNull private Observable<List<RecommendedVenuesGroup>> getRecommendedVenuesNear(
            @NonNull String location)
    {
        return foursquareService.exploreVenuesByNear(location)
                .flatMap(new Func1<ExploreResponse, Observable<List<RecommendedVenuesGroup>>>()
                {
                    @Override
                    public Observable<List<RecommendedVenuesGroup>> call(ExploreResponse exploreResponse)
                    {
                        if (exploreResponse.getMeta().getCode() != com.foursquare.Constants.RESPONSE_SUCCESSFUL)
                        {
                            Log.v(TAG, exploreResponse.toString());
                            return Observable.error(new IllegalArgumentException("Response code not 200"));
                        }
                        return Observable.just(exploreResponse.getResponse().getGroups());
                    }
                });
    }
}
