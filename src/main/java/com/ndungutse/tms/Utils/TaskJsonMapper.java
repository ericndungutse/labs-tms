package com.ndungutse.tms.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ndungutse.tms.dot.AllTasksResponse;
import com.ndungutse.tms.dot.TaskDTO;

import java.io.IOException;

public class TaskJsonMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.registerModule(new JavaTimeModule());
        // Prevent date as an array
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    private TaskJsonMapper() {}

    public static TaskDTO fromJson(String json) throws IOException {
        return objectMapper.readValue(json, TaskDTO.class);
    }

    public static String toJson(TaskDTO taskDTO) throws JsonProcessingException {
        return objectMapper.writeValueAsString(taskDTO);
    }

    public static String toJsonFromResponse(AllTasksResponse allTasksResponse) throws JsonProcessingException {
        return objectMapper.writeValueAsString(allTasksResponse);
    }

}
