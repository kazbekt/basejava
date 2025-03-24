package com.urise.webapp.util;

import com.google.gson.*;
import com.urise.webapp.model.Organization;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class JsonPeriodAdapter implements JsonSerializer<Organization.Period>, JsonDeserializer<Organization.Period> {
    @Override
    public JsonElement serialize(Organization.Period period, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("startDate", context.serialize(period.getStartDate()));
        jsonObject.add("endDate", context.serialize(period.getEndDate()));
        jsonObject.addProperty("title", period.getTitle());
        jsonObject.addProperty("description", period.getDescription());
        return jsonObject;
    }

    @Override
    public Organization.Period deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Organization.Period(
                context.deserialize(jsonObject.get("startDate"), LocalDate.class),
                context.deserialize(jsonObject.get("endDate"), LocalDate.class),
                jsonObject.get("title").getAsString(),
                jsonObject.get("description").getAsString()
        );
    }
}