package com.foursquare.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class GroupType implements Parcelable
{
    @JsonValue
    @NonNull abstract public String getType();

    @JsonCreator public static GroupType create(@NonNull String type)
    {
        return new AutoParcel_GroupType(type);
    }
}
