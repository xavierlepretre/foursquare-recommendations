package com.foursquare.service;

import android.annotation.SuppressLint;
import android.support.test.runner.AndroidJUnit4;
import android.test.FlakyTest;

import com.foursquare.Constants;
import com.foursquare.credentials.Keys;
import com.foursquare.model.ExploreResponse;
import com.foursquare.model.Location;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class FourSquareRequestTest
{
    private FoursquareService service;

    @Rule
    public IsConnectedTestRule isConnectedTestRule = new IsConnectedTestRule();

    @Before @SuppressLint("SimpleDateFormat")
    public void setUp()
    {
        this.service = new FoursquareService(
                Keys.create(
                        "LEKR3QGOLVGL2QQHCJ4RWAVWAKIRTFJKQWA34QZ41RM0LZP3",
                        "B2FU1WAZ1UDZPBFCPIVSEPDXUIBFYTO21ZNK2DU4IVHOZQWW"),
                Constants.VERSION);
    }

    @Test @FlakyTest(tolerance = 3)
    public void canExploreVenues() throws Exception
    {
        Iterator<ExploreResponse> iterator = service
                .exploreVenues(
                        Location.create(null, 40.7, -74))
                .toBlocking()
                .getIterator();
        ExploreResponse response = iterator.next();
        assertThat(iterator.hasNext()).isFalse();
        assertThat(response.getMeta().getCode()).isEqualTo(200);
        assertThat(response.getResponse().getGroups().size()).isGreaterThanOrEqualTo(1);
        assertThat(response.getResponse().getGroups().get(0).getItems().size()).isGreaterThanOrEqualTo(5);
    }
}
