package com.ndungutse.tms.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ndungutse.tms.dot.AllTasksResponse;
import com.ndungutse.tms.dot.TaskDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TaskJsonMapper {
    private static final Logger logger = LoggerFactory.getLogger(TaskJsonMapper.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
        // Prevent date as an array
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private TaskJsonMapper() {}

    public static TaskDTO fromJson(String json) throws IOException {
        try {
            return objectMapper.readValue(json, TaskDTO.class);
        } catch (IOException e) {
            logger.error("Failed to deserialize TaskDTO from JSON: {}", json, e);
            throw e;
        }
    }

    public static String toJson(TaskDTO taskDTO) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(taskDTO);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize TaskDTO to JSON: {}", taskDTO, e);
            throw e;
        }
    }

    public static String toJsonFromResponse(AllTasksResponse allTasksResponse) throws JsonProcessingException {
        try {
            return objectMapper.writeValueAsString(allTasksResponse);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize AllTasksResponse to JSON: {}", allTasksResponse, e);
            throw e;
        }
    }
}
