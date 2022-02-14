package com.lmk.architecture.net.base;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CustomAdapterFactory implements TypeAdapterFactory {
    private static final Set<String> TRUE_STRINGS = new HashSet<>(Arrays.asList("true", "1", "yes"));

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType == Boolean.class) {
            return (TypeAdapter<T>) booleanAdapter;
        }
        return null;
    }


    private static final TypeAdapter<Boolean> booleanAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter jsonWriter, Boolean s) throws IOException {

        }

        @Override
        public Boolean read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.BOOLEAN) {
                return reader.nextBoolean();
            }
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return false;
            }
            if (reader.peek() == JsonToken.NUMBER) {
                return reader.nextInt() == 1;
            }
            if (reader.peek() == JsonToken.STRING) {
                return TRUE_STRINGS.contains(reader.nextString().toLowerCase());
            }
            return false;
        }
    };
}