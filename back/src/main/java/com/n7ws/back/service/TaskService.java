package com.n7ws.back.service;

import com.n7ws.back.tasks.Task;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private Map<String, List<Task>> tasks;

    public TaskService() {
        this.tasks = new HashMap<String, List<Task>>();
    }

    public boolean hasTasks(String deviceName) {
        List<Task> queue = tasks.get(deviceName);
        if (queue != null) {
            return (queue.size() > 0);
        } else {
            return false;
        }
        // return true; // for testing purposes
    }

    public Task getPriorityTask(String deviceName) {
        List<Task> queue = tasks.get(deviceName);
        if (queue != null) { // there are tasks
            try {
                Task task = queue.get(0);
                queue.remove(0);
                return task;
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
        // hardcoded dummy values for testing purposes
        // return new Task("taskName", "taskPath"); // to rm
        return null;
    }

    public void addTask(String device, Task task) {
        List<Task> deviceTasks = tasks.get(device);
        System.out.println("Adding task: " + task + " to device: " + device);
        if (deviceTasks != null) {
            deviceTasks.add(task);
        } else {
            deviceTasks = new ArrayList<Task>();
            deviceTasks.add(task);
            tasks.put(device, deviceTasks);
        }
    }
}
