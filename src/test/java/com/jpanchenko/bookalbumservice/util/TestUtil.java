package com.jpanchenko.bookalbumservice.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestUtil {

    @SneakyThrows
    public static <T> T toInternalObject(String jsonPath, Class clazzPath, Class<T> objectClass) {
        String jsonContent = getJsonContent(clazzPath, jsonPath);
        return createObjectMapper().readValue(jsonContent, objectClass);
    }

    @SneakyThrows
    private static String getJsonContent(Class clazz, String jsonFilename) {
        InputStream inputStream = clazz.getResourceAsStream(jsonFilename);
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }

}
