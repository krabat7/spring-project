package com.javarush.dao;

import com.javarush.domain.Task;

import java.util.List;

public interface TaskDAO {
    public List<Task> getAll(int offset, int limit);
    public int getCount();
    public Task getById(Integer id);
    public void saveOrUpdate(Task task);
    public void delete(Task task);
}
