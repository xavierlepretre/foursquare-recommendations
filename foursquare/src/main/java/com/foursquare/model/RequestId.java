package com.foursquare.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class RequestId implements Parcelable
{
    @JsonValue
    @NonNull abstract public String getId();

    @JsonCreator public static RequestId create(@NonNull String id)
    {
        return new AutoParcel_RequestId(id);
    }
}
