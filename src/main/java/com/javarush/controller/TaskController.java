package com.javarush.controller;

import com.javarush.dao.TaskDAO;
import com.javarush.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import com.javarush.domain.Task;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String showAllTasks(Model model,
                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                               @RequestParam(value = "limit", required = false, defaultValue = "6") int limit) {
        int offset = (page - 1) * limit;
        List<Task> allTasks = taskService.getAll(offset, limit);
        model.addAttribute("allTasks", allTasks);
        model.addAttribute("currentPage", page);
        int pages = (int) Math.ceil(1.0 * taskService.getCount() / limit);
        if(pages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, pages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "index";
    }

    @PostMapping("/{id}")
    public String editTask(Model model,
                         @PathVariable Integer id,
                         @RequestBody TaskInfo info) {
        if(isNull(id) || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }

        Task task = taskService.edit(id, info.getDescription(), info.getStatus());
        return "index";
    }

    @PostMapping("/")
    public String addTask(Model model,
                         @RequestBody TaskInfo info) {
        Task task = taskService.create(info.getDescription(), info.getStatus());

        return "index";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(Model model,
                             @PathVariable Integer id) {
        if(isNull(id) || id <= 0) {
            throw new IllegalArgumentException("Invalid id");
        }

        taskService.delete(id);
        return "index";
    }
}
