package com.foursquare.service;

import android.annotation.SuppressLint;
import android.support.test.runner.AndroidJUnit4;
import android.test.FlakyTest;

import com.foursquare.Constants;
import com.foursquare.credentials.Keys;
import com.foursquare.model.ExploreResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import retrofit.JacksonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(AndroidJUnit4.class)
public class FourSquareRetrofitRequestTest
{
    private FoursquareServiceRetrofit retrofit;
    private Keys keys;
    private String version;

    @Rule
    public IsConnectedTestRule isConnectedTestRule = new IsConnectedTestRule();

    @Before @SuppressLint("SimpleDateFormat")
    public void setUp()
    {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(Constants.END_POINT)
                .addConverterFactory(JacksonConverterFactory.create(
                        ObjectMapperFactory.create()
                ))
                .build()
                .create(FoursquareServiceRetrofit.class);
        this.keys = Keys.create(
                "LEKR3QGOLVGL2QQHCJ4RWAVWAKIRTFJKQWA34QZ41RM0LZP3",
                "B2FU1WAZ1UDZPBFCPIVSEPDXUIBFYTO21ZNK2DU4IVHOZQWW");
        version = Constants.VERSION;
    }

    @Test @FlakyTest(tolerance = 3)
    public void canExploreVenues() throws Exception
    {
        Response<ExploreResponse> response = retrofit
                .exploreVenues(
                        keys.getClientId(),
                        keys.getClientSecret(),
                        version,
                        "40.7,-74")
                .execute();
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.body().getMeta().getCode()).isEqualTo(200);
        assertThat(response.body().getResponse().getGroups().size()).isGreaterThanOrEqualTo(1);
        assertThat(response.body().getResponse().getGroups().get(0).getItems().size()).isGreaterThanOrEqualTo(5);
    }

}
