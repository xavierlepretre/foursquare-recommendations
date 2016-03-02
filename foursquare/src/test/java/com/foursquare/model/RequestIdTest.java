package com.foursquare.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class RequestIdTest
{
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception
    {
        mapper = new ObjectMapper();
    }

    @Test
    public void testCanDeserialise() throws Exception
    {
        assertThat(mapper.readValue(getClass().getResourceAsStream("request_id_1.json"), RequestId.class))
                .isEqualTo(RequestId.create("56d6c93e498ece642c6373b2"));
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        assertThat(mapper.writeValueAsString(RequestId.create("56d6c93e498ece642c6373b2")))
                .isEqualTo("\"56d6c93e498ece642c6373b2\"");
    }
}