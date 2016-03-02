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
import com.foursquare.model.RecommendedVenuesResponse;
import com.foursquare.service.FoursquareService;
import com.github.xavierlepretre.foursquarerecommendations.rx.ViewObservable;
import com.github.xavierlepretre.rxdialog.AlertDialogButtonEvent;
import com.github.xavierlepretre.rxdialog.AlertDialogEvent;
import com.github.xavierlepretre.rxdialog.support.RxAlertDialogSupport;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.BehaviorSubject;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{
    public static final String TAG = MainActivity.class.getSimpleName();
    private static final int ZOOM_PADDING = 0;

    @VisibleForTesting BehaviorSubject<GoogleMap> mapSubject;
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
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapSubject = BehaviorSubject.create();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        foursquareService = new FoursquareService(Keys.create(Constants.CLIENT_ID, Constants.CLIENT_SECRET));
        placeSubscription = getDesiredPlaceName(fab)
                .flatMap(new Func1<String, Observable<RecommendedVenuesResponse>>()
                {
                    @Override
                    public Observable<RecommendedVenuesResponse> call(String desiredPlace)
                    {
                        MainActivity.this.desiredPlace = desiredPlace;
                        return getRecommendedVenuesNear(desiredPlace);
                    }
                })
                .doOnNext(new Action1<RecommendedVenuesResponse>()
                {
                    @Override public void call(RecommendedVenuesResponse recommendedVenuesResponse)
                    {
                        MainActivity.this.recommendedVenuesGroups = recommendedVenuesResponse.getGroups();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .withLatestFrom(
                        mapSubject,
                        new Func2<RecommendedVenuesResponse, GoogleMap, GoogleMap>()
                        {
                            @Override
                            public GoogleMap call(RecommendedVenuesResponse recommendedVenuesResponse, GoogleMap map)
                            {
                                populateMap(map, recommendedVenuesResponse.getGroups());
                                map.animateCamera(CameraUpdateFactory.create(
                                        recommendedVenuesResponse.getSuggestedBounds(),
                                        ZOOM_PADDING));
                                return map;
                            }
                        })
                .subscribe(
                        new Action1<GoogleMap>()
                        {
                            @Override public void call(GoogleMap map)
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
        mapSubject.onNext(googleMap);
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
                                        return input.getText().toString().trim();
                                    }
                                });
                    }
                });
    }

    @NonNull private Observable<RecommendedVenuesResponse> getRecommendedVenuesNear(
            @NonNull String location)
    {
        return foursquareService.exploreVenuesByNear(location)
                .flatMap(new Func1<ExploreResponse, Observable<RecommendedVenuesResponse>>()
                {
                    @Override
                    public Observable<RecommendedVenuesResponse> call(ExploreResponse exploreResponse)
                    {
                        if (exploreResponse.getMeta().getCode() != com.foursquare.Constants.RESPONSE_SUCCESSFUL)
                        {
                            Log.v(TAG, exploreResponse.toString());
                            return Observable.error(new IllegalArgumentException("Response code not 200"));
                        }
                        return Observable.just(exploreResponse.getResponse());
                    }
                });
    }

    private void populateMap(
            @NonNull GoogleMap map,
            @NonNull List<RecommendedVenuesGroup> recommendedVenuesGroups)
    {
        for (MarkerOptions marker : MarkerOptionsFactory.create(recommendedVenuesGroups))
        {
            map.addMarker(marker);
        }
    }
}
