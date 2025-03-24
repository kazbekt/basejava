package com.urise.webapp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.urise.webapp.util.JsonOrganizationAdapter;
import com.urise.webapp.util.JsonPeriodAdapter;
import com.urise.webapp.util.JsonSectionAdapter;
import com.urise.webapp.util.LocalDateJsonAdapter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class JsonParserOrganizationSectionTest {

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Section.class, new JsonSectionAdapter())
            .registerTypeAdapter(Organization.class, new JsonOrganizationAdapter())
            .registerTypeAdapter(Organization.Period.class, new JsonPeriodAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
            .create();

    public static Section theSection = new OrganizationSection(new ArrayList<>());
    public static Organization theOrganization = new Organization("Test Organization", "http://test.com");

    @Before
    public void setUp() {
        theOrganization.addPeriod(new Organization.Period( LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 1, 1),
                "Developer",
                "Developed awesome software"));

        ((OrganizationSection) theSection).getOrganizations().add(theOrganization);
    }


    @Test
    public void testOrganizationSerialization() {
        Organization organization = new Organization("Test Organization", "http://test.com");
        organization.addPeriod(new Organization.Period(
                LocalDate.of(2020, 1, 1),
                LocalDate.of(2022, 1, 1),
                "Developer",
                "Developed awesome software"
        ));

        OrganizationSection section = new OrganizationSection(new ArrayList<>());
        section.getOrganizations().add(organization);

        String json = GSON.toJson(theSection, Section.class); // 🔥 Явно указываем Section.class
        System.out.println("Serialized JSON: " + json);

        Section deserializedSection = GSON.fromJson(json, Section.class);
        System.out.println("Deserialized Object: " + deserializedSection);

        assertEquals("Десериализованный объект не совпадает с оригиналом!", theSection, deserializedSection);
    }


    public static void main(String[] args) {
        JsonParserOrganizationSectionTest test = new JsonParserOrganizationSectionTest();
        test.setUp();
        test.testOrganizationSerialization();

    }
}
