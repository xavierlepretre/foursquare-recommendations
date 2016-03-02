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
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.github.xavierlepretre.foursquarerecommendations.rx.ViewObservable;
import com.github.xavierlepretre.rxdialog.AlertDialogButtonEvent;
import com.github.xavierlepretre.rxdialog.AlertDialogEvent;
import com.github.xavierlepretre.rxdialog.support.RxAlertDialogSupport;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback
{
    public static final String TAG = MainActivity.class.getSimpleName();

    private FloatingActionButton fab;
    private Subscription placeSubscription;

    @VisibleForTesting String desiredPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        placeSubscription = getDesiredPlaceName(fab)
                .subscribe(
                        new Action1<String>()
                        {
                            @Override public void call(String desiredPlace)
                            {
                                MainActivity.this.desiredPlace = desiredPlace;
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
        final EditText input = (EditText) LayoutInflater.from(this)
                .inflate(R.layout.place_input, null);

        return ViewObservable.clicked(fab)
                .flatMap(new Func1<View, Observable<AlertDialogEvent>>()
                {
                    @Override public Observable<AlertDialogEvent> call(View view)
                    {
                        return new RxAlertDialogSupport.Builder(MainActivity.this)
                                .title(R.string.dialog_place_title)
                                .view(input)
                                .positiveButton(android.R.string.ok)
                                .neutralButton(android.R.string.cancel)
                                .show();
                    }
                })
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
}
