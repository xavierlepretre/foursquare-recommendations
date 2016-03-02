package com.foursquare.credentials;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class Keys implements Parcelable
{
    @NonNull abstract public String getClientId();

    @NonNull abstract public String getClientSecret();

    @NonNull public static Keys create(@NonNull String clientId, @NonNull String clientSecret)
    {
        return new AutoParcel_Keys(clientId, clientSecret);
    }
}
