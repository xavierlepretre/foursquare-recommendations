package com.foursquare.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class LocationTest
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
        Location created = mapper.readValue(getClass().getResourceAsStream("location_1.json"), Location.class);
        assertThat(created.getAddress()).isEqualTo("Furman St");
        assertThat(created.getLat()).isEqualTo(40.699511638395514);
        assertThat(created.getLon()).isEqualTo(-73.99813359642076);
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        Location location = Location.create("abc", 12, 34);
        assertThat(mapper.writeValueAsString(location))
                .isEqualTo("{\"address\":\"abc\",\"lat\":12.0,\"lng\":34.0}");
    }

    @Test
    public void testGetQueryForm1() throws Exception
    {
        Location location = Location.create("abc", 12.21, 34.43);
        assertThat(location.getQueryForm()).isEqualTo("12.21,34.43");
    }

    @Test
    public void testGetQueryForm2() throws Exception
    {
        Location location = Location.create("abc", 12.21, -34.43);
        assertThat(location.getQueryForm()).isEqualTo("12.21,-34.43");
    }

    @Test
    public void testGetQueryForm3() throws Exception
    {
        Location location = Location.create("abc", -12.21, 34.43);
        assertThat(location.getQueryForm()).isEqualTo("-12.21,34.43");
    }

    @Test
    public void testGetQueryForm4() throws Exception
    {
        Location location = Location.create("abc", -12.21, -34.43);
        assertThat(location.getQueryForm()).isEqualTo("-12.21,-34.43");
    }
}