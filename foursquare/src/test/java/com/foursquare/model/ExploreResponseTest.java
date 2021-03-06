package com.foursquare.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.fest.assertions.api.Assertions.assertThat;

public class ExploreResponseTest
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
        ExploreResponse created = mapper.readValue(getClass().getResourceAsStream("explore_response_1.json"),
                ExploreResponse.class);
        assertThat(created.getMeta()).isEqualTo(ResponseMeta.create(200, RequestId.create("56d6c93e498ece642c6373b2")));
        assertThat(created.getResponse().getGroups().get(0).getType()).isEqualTo(GroupType.create("Recommended Places"));
        assertThat(created.getResponse().getGroups().get(0).getItems().size()).isGreaterThan(5);
        assertThat(created.getResponse().getGroups().get(0).getItems().get(0)).isEqualTo(RecommendedVenue.create(
                Venue.create(
                        VenueId.create("51eabef6498e10cf3aea7942"),
                        "Brooklyn Bridge Park - Pier 2",
                        Location.create("Furman St", 40.699511638395514, -73.99813359642076)),
                ReferralId.create("e-0-51eabef6498e10cf3aea7942-0")));
        assertThat(created.getResponse().getGroups().get(0).getName()).isEqualTo("recommended");
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        ExploreResponse venue = ExploreResponse.create(
                ResponseMeta.create(200, RequestId.create("stu")),
                RecommendedVenuesResponse.create(
                        SuggestedBounds.create(
                                Location.create(null, 21, 43),
                                Location.create(null, 65, 87)),
                        Collections.singletonList(
                                RecommendedVenuesGroup.create(
                                        GroupType.create("mno"),
                                        "pqr",
                                        Collections.singletonList(
                                                RecommendedVenue.create(
                                                        Venue.create(
                                                                VenueId.create("abc"),
                                                                "def",
                                                                Location.create("ghi", 12, 34)),
                                                        ReferralId.create("jkl")))))));
        assertThat(mapper.writeValueAsString(venue))
                .isEqualTo("{\"meta\":{\"code\":200,\"requestId\":\"stu\"}," +
                        "\"response\":{" +
                        "\"suggestedBounds\":{\"ne\":{\"address\":null,\"lat\":21.0,\"lng\":43.0}," +
                        "\"sw\":{\"address\":null,\"lat\":65.0,\"lng\":87.0}}," +
                        "\"groups\":[{\"type\":\"mno\",\"name\":\"pqr\",\"items\":[" +
                        "{\"venue\":{\"id\":\"abc\",\"name\":\"def\"," +
                        "\"location\":{\"address\":\"ghi\",\"lat\":12.0,\"lng\":34.0}},\"referralId\":\"jkl\"}]}]}}");
    }
}