package com.urise.webapp.util;

import com.google.gson.*;
import com.urise.webapp.model.Section;

import java.lang.reflect.Type;

public class JsonSectionAdapter implements JsonSerializer<Section>, JsonDeserializer<Section> {
    private static final String CLASSNAME = "CLASSNAME";
    private static final String INSTANCE = "INSTANCE";

    @Override
    public JsonElement serialize(Section section, Type type, JsonSerializationContext context) {
        if (section == null) {
            return JsonNull.INSTANCE;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, section.getClass().getName());
        jsonObject.add(INSTANCE, context.serialize(section));
        return jsonObject;
    }

    @Override
    public Section deserialize(JsonElement json, Type type, JsonDeserializationContext context)
            throws JsonParseException {

        if (json == null || json.isJsonNull()) {
            return null;
        }

        if (!json.isJsonObject()) {
            throw new JsonParseException("Expected JSON object, got: " + json);
        }

        JsonObject jsonObject = json.getAsJsonObject();

        if (!jsonObject.has(CLASSNAME)) {
            throw new JsonParseException("JSON must contain '" + CLASSNAME + "' field");
        }

        String className = jsonObject.get(CLASSNAME).getAsString();

        if (!jsonObject.has(INSTANCE)) {
            throw new JsonParseException("JSON must contain '" + INSTANCE + "' field");
        }

        JsonElement instanceElement = jsonObject.get(INSTANCE);

        try {
            Class<?> clazz = Class.forName(className);

            if (!Section.class.isAssignableFrom(clazz)) {
                throw new JsonParseException("Class " + className + " is not a subtype of Section");
            }

            return context.deserialize(instanceElement, clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException("Class not found: " + className, e);
        }
    }
}