package com.foursquare.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.fest.assertions.api.Assertions.assertThat;

public class RecommendedVenuesResponseTest
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
        RecommendedVenuesResponse created = mapper.readValue(getClass().getResourceAsStream("recommended_venues_response_1.json"),
                RecommendedVenuesResponse.class);
        assertThat(created.getGroups().get(0).getType()).isEqualTo(GroupType.create("Recommended Places"));
        assertThat(created.getGroups().get(0).getItems().size()).isGreaterThan(5);
        assertThat(created.getGroups().get(0).getItems().get(0)).isEqualTo(RecommendedVenue.create(
                Venue.create(
                        VenueId.create("51eabef6498e10cf3aea7942"),
                        "Brooklyn Bridge Park - Pier 2",
                        Location.create("Furman St", 40.699511638395514, -73.99813359642076)),
                ReferralId.create("e-0-51eabef6498e10cf3aea7942-0")));
        assertThat(created.getGroups().get(0).getName()).isEqualTo("recommended");
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        RecommendedVenuesResponse venue = RecommendedVenuesResponse.create(Collections.singletonList(
                RecommendedVenuesGroup.create(
                        GroupType.create("mno"),
                        "pqr",
                        Collections.singletonList(
                                RecommendedVenue.create(
                                        Venue.create(
                                                VenueId.create("abc"),
                                                "def",
                                                Location.create("ghi", 12, 34)),
                                        ReferralId.create("jkl"))))));
        assertThat(mapper.writeValueAsString(venue))
                .isEqualTo("{\"groups\":[{\"type\":\"mno\",\"name\":\"pqr\",\"items\":[{\"venue\":{\"id\":\"abc\",\"name\":\"def\"," +
                        "\"location\":{\"address\":\"ghi\",\"lat\":12.0,\"lng\":34.0}},\"referralId\":\"jkl\"}]}]}");
    }
}