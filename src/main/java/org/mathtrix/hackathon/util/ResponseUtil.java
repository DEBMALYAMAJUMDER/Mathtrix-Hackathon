package org.mathtrix.hackathon.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class ResponseUtil {
    private ResponseUtil() {

    }

    public static String saveResponseToFile(String response, String fileName) {

        try {
            Path path = Path.of(fileName);
            Files.createDirectories(path.getParent() != null ? path.getParent() : Path.of("/resources"));

            // Parse and pretty-print the JSON
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter prettyWriter = mapper.writerWithDefaultPrettyPrinter();
            Object json = mapper.readValue(response, Object.class);
            String prettyJson = prettyWriter.writeValueAsString(json);

            // Write formatted JSON to file
            try (FileWriter writer = new FileWriter(path.toFile())) {
                writer.write(prettyJson);
            }
            return "✅ Code review result saved (formatted) to file:" + path.toAbsolutePath();
        } catch (IOException e) {
            log.error("❌ Failed to save code review result to file", e);
        }
        return "❌ Failed to save code review result to file ";
    }
}
