package com.foursquare.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ReferralIdTest
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
        assertThat(mapper.readValue(getClass().getResourceAsStream("referral_id_1.json"), ReferralId.class))
                .isEqualTo(ReferralId.create("e-0-51eabef6498e10cf3aea7942-0"));
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        assertThat(mapper.writeValueAsString(ReferralId.create("abc")))
                .isEqualTo("\"abc\"");
    }
}