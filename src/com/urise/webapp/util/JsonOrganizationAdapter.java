package com.urise.webapp.util;

import com.google.gson.*;
import com.urise.webapp.model.Organization;

import java.lang.reflect.Type;

public class JsonOrganizationAdapter implements JsonSerializer<Organization>, JsonDeserializer<Organization> {
    @Override
    public JsonElement serialize(Organization org, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("homePage", context.serialize(org.getHomePage()));

        JsonArray periodsArray = new JsonArray();
        for (Organization.Period period : org.getPeriods()) {
            periodsArray.add(context.serialize(period));
        }
        jsonObject.add("periods", periodsArray);

        return jsonObject;
    }

    @Override
    public Organization deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonObject homePageObj = jsonObject.getAsJsonObject("homePage");

        String homePageName = homePageObj.get("name").getAsString();
        String homePageUrl = homePageObj.has("url") ? homePageObj.get("url").getAsString() : null;

        Organization organization = new Organization(homePageName, homePageUrl);

        JsonArray periodsArray = jsonObject.getAsJsonArray("periods");
        for (JsonElement periodElement : periodsArray) {
            organization.addPeriod(context.deserialize(periodElement, Organization.Period.class));
        }

        return organization;
    }
}