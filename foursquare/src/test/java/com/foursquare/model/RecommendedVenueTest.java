package com.foursquare.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class RecommendedVenueTest
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
        RecommendedVenue created = mapper.readValue(getClass().getResourceAsStream("recommended_venue_1.json"), RecommendedVenue.class);
        assertThat(created.getVenue()).isEqualTo(Venue.create(
                VenueId.create("51eabef6498e10cf3aea7942"),
                "Brooklyn Bridge Park - Pier 2",
                Location.create("Furman St", 40.699511638395514, -73.99813359642076)));
        assertThat(created.getReferralId()).isEqualTo(ReferralId.create("e-0-51eabef6498e10cf3aea7942-0"));
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        RecommendedVenue venue = RecommendedVenue.create(
                Venue.create(
                        VenueId.create("abc"),
                        "def",
                        Location.create("ghi", 12, 34)),
                ReferralId.create("jkl"));
        assertThat(mapper.writeValueAsString(venue))
                .isEqualTo("{\"venue\":{\"id\":\"abc\",\"name\":\"def\"," +
                        "\"location\":{\"address\":\"ghi\",\"lat\":12.0,\"lng\":34.0}}," +
                        "\"referralId\":\"jkl\"}");
    }
}