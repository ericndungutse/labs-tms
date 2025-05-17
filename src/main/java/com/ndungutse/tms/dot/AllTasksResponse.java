package com.ndungutse.tms.dot;

import java.util.List;

public class AllTasksResponse {
    //    private int total;
//    private int page;
//    private int pageSize;
    private String status;
    private Data data;

    public AllTasksResponse(Data data, String success) {
        this.data = data;
        this.status = success;
    }
    public static class Data {
        private List<TaskDTO> tasks;

        public Data(List<TaskDTO> tasks) {
            this.tasks = tasks;
        }

        public List<TaskDTO> getTasks() {
            return tasks;
        }

        public void setTasks(List<TaskDTO> tasks) {
            this.tasks = tasks;
        }
    }
    public AllTasksResponse(String status, List<TaskDTO> tasks) {
        this.status = status;
        this.data = new Data(tasks);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AllTasksResponse{" +
                "status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}
