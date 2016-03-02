package com.foursquare.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import auto.parcel.AutoParcel;

@AutoParcel
abstract public class ResponseMeta implements Parcelable
{
    public static final String KEY_CODE = "code";
    public static final String KEY_REQUEST_ID = "requestId";

    @JsonProperty(KEY_CODE)
    abstract public int getCode();

    @JsonProperty(KEY_REQUEST_ID)
    abstract public RequestId getRequestId();

    @JsonCreator
    @NonNull public static ResponseMeta create(
            @JsonProperty(KEY_CODE) int code,
            @JsonProperty(KEY_REQUEST_ID) RequestId requestId)
    {
        return new AutoParcel_ResponseMeta(code, requestId);
    }
}
