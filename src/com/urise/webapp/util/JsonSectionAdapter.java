package com.urise.webapp.util;

import com.google.gson.*;
import com.urise.webapp.model.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

public class JsonSectionAdapter implements JsonSerializer<Section>, JsonDeserializer<Section> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Organization.class, new JsonOrganizationAdapter()) // Только для Organization
            .registerTypeAdapter(Organization.Period.class, new JsonPeriodAdapter())
            .registerTypeAdapter(LocalDate.class, new LocalDateJsonAdapter())
            .create();

    @Override
    public Section deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String className = jsonObject.get(CLASSNAME).getAsString();

        try {
            Class<?> clazz = Class.forName(className);

            if (OrganizationSection.class.isAssignableFrom(clazz)) {
                // Десериализуем OrganizationSection отдельно
                return GSON.fromJson(jsonObject.get(INSTANCE), OrganizationSection.class);
            }

            return context.deserialize(jsonObject.get(INSTANCE), clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Unknown class: " + className, e);
        }
    }

    @Override
    public JsonElement serialize(Section section, Type type, JsonSerializationContext context) {
        JsonObject retValue = new JsonObject();
        retValue.addProperty(CLASSNAME, section.getClass().getName());

        if (section instanceof OrganizationSection) {
            JsonObject organizationSectionJson = new JsonObject();
            JsonArray organizationsArray = new JsonArray();

            // Сериализуем все организации в разделе
            for (Organization organization : ((OrganizationSection) section).getOrganizations()) {
                organizationsArray.add(context.serialize(organization));
            }
            organizationSectionJson.add("organizations", organizationsArray);
            retValue.add(INSTANCE, organizationSectionJson);
        } else {
            retValue.add(INSTANCE, context.serialize(section));
        }
        return retValue;
    }
}