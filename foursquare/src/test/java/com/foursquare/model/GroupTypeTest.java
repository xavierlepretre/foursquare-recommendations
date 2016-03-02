package com.foursquare.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class GroupTypeTest
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
        assertThat(mapper.readValue(getClass().getResourceAsStream("group_type_1.json"), GroupType.class))
                .isEqualTo(GroupType.create("Recommended Places"));
    }

    @Test
    public void testCanSerialise() throws Exception
    {
        assertThat(mapper.writeValueAsString(GroupType.create("Recommended Places")))
                .isEqualTo("\"Recommended Places\"");
    }
}