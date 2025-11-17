package org.mathtrix.hackathon.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.mathtrix.hackathon.constant.APIConstant;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class ResponseUtil {
    private ResponseUtil() {

    }

    public static String getDateInString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern(APIConstant.YYYY_MM_DD));
    }

    public static String getResponseMessage(String response) {
        try {
            // Parse and pretty-print the JSON
            ObjectMapper mapper = new ObjectMapper();
            ObjectWriter prettyWriter = mapper.writerWithDefaultPrettyPrinter();
            Object json = mapper.readValue(response, Object.class);
            String prettyJson = prettyWriter.writeValueAsString(json);
            JsonNode jsonNode = mapper.readTree(prettyJson);
            String reviewMessage = jsonNode.get(APIConstant.MESSAGE).asText();
            log.info("✅ Code review result Fetched Successfully,Message : [{}]", reviewMessage);
            return reviewMessage;
        } catch (IOException e) {
            log.error("❌ Failed to Fetch code review result", e);
        }
        return "❌ Failed to Fetch code review result";
    }

    public static String saveResponseToFile(String response, String fileName) {

        try {
            Path path = Path.of(fileName);
            Files.createDirectories(path.getParent() != null ? path.getParent() : Path.of("/resources"));


            String prettyJson = getResponseMessage(response);

            // Write formatted JSON to file
            try (FileWriter writer = new FileWriter(path.toFile())) {
                writer.write(prettyJson);
            }
            log.info("✅ Code review result saved (formatted) to file:" + path.toAbsolutePath());
            return "✅ Code review result saved (formatted) to file:" + path.toAbsolutePath();
        } catch (IOException e) {
            log.error("❌ Failed to save code review result to file", e);
        }
        return "❌ Failed to save code review result to file ";
    }
}
