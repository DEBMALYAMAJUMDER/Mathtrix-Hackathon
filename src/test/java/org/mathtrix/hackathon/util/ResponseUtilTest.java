package org.mathtrix.hackathon.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class ResponseUtilTest {
    @Test
    void testConstructor() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<ResponseUtil> constructor = ResponseUtil.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        ResponseUtil instance = constructor.newInstance();
        assertNotNull(instance);
    }

    @Test
    void testGetDateInString() {
        LocalDateTime dateTime = LocalDateTime.of(2023, 10, 5, 12, 0);
        String result = ResponseUtil.getDateInString(dateTime);
        assertEquals("2023-10-05", result);
    }

    @Test
    void testGetResponseMessage_ValidJson() {
        String jsonResponse = "{\"message\": \"Code review successful\", \"status\": \"OK\"}";
        String result = ResponseUtil.getResponseMessage(jsonResponse);
        assertEquals("Code review successful", result);
    }

    @Test
    void testGetResponseMessage_InvalidJson() {
        String invalidJson = "{invalid json}";
        String result = ResponseUtil.getResponseMessage(invalidJson);
        assertEquals("❌ Failed to Fetch code review result", result);
    }

    @Test
    void testSaveResponseToFile_Success(@TempDir Path tempDir) throws IOException {
        String jsonResponse = "{\"message\": \"Review done\"}";
        Path filePath = tempDir.resolve("review.txt");

        String result = ResponseUtil.saveResponseToFile(jsonResponse, filePath.toString());

        assertTrue(result.contains("✅ Code review result saved (formatted) to file:"));
        assertTrue(result.contains(filePath.toAbsolutePath().toString()));
        assertTrue(Files.exists(filePath));

        String content = Files.readString(filePath);
        assertEquals("Review done", content);
    }

    @Test
    void testSaveResponseToFile_IOException(@TempDir Path tempDir) {
        String jsonResponse = "{\"message\": \"Review done\"}";
        // Passing a directory as filename usually causes IOException when trying to write to it as a file
        Path directoryAsFile = tempDir.resolve("existingDir");
        try {
            Files.createDirectory(directoryAsFile);
        } catch (IOException e) {
            fail("Failed to set up test directory");
        }

        String result = ResponseUtil.saveResponseToFile(jsonResponse, directoryAsFile.toString());

        assertEquals("❌ Failed to save code review result to file ", result);
    }
}