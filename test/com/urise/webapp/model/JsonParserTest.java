package com.urise.webapp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urise.webapp.model.Organization;
import com.urise.webapp.util.JsonOrganizationAdapter;
import com.urise.webapp.util.JsonPeriodAdapter;
import com.urise.webapp.util.LocalDateJsonAdapter;
import org.junit.Test;

import java.time.LocalDate;

public class JsonParserTest {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Organization.class, new JsonOrganizationAdapter())
            .registerTypeAdapter(Organization.Period.class, new JsonPeriodAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
            .create();

    @Test
    public void testOrganizationSerialization() {
        Organization organization = new Organization("Test Organization", "http://test.com");
        organization.addPeriod(new Organization.Period(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 1, 1),
                "Developer",
                "Developed awesome software"
        ));

        String json = GSON.toJson(organization);
        System.out.println("Serialized JSON: " + json);

        Organization deserializedOrganization = GSON.fromJson(json, Organization.class);
        System.out.println("Deserialized Object: " + deserializedOrganization);
    }
}
