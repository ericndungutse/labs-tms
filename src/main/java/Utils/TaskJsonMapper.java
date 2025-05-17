package Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Task;

import java.io.IOException;

public class TaskJsonMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private TaskJsonMapper() {}

    public static Task fromJson(String json) throws IOException {
        return objectMapper.readValue(json, Task.class);
    }

    public static String toJson(Task task) throws JsonProcessingException {
        return objectMapper.writeValueAsString(task);
    }
}
