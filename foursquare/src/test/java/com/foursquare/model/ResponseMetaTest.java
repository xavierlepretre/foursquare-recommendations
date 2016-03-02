package com.foursquare.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ResponseMetaTest
{
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception
    {
        mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Test
    public void testCanDeserialise() throws Exception
    {
        ResponseMeta created = mapper.readValue(getClass().getResourceAsStream("response_meta_1.json"), ResponseMeta.class);
        assertThat(created.getCode()).isEqualTo(200);
        assertThat(created.getRequestId()).isEqualTo(RequestId.create("56d6c93e498ece642c6373b2"));
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        ResponseMeta responseMeta = ResponseMeta.create(12, RequestId.create("abc"));
        assertThat(mapper.writeValueAsString(responseMeta))
                .isEqualTo("{\"code\":12,\"requestId\":\"abc\"}");
    }
}