package com.kodilla.hibernate.tasklist.dao;

import com.kodilla.hibernate.tasklist.TaskList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TaskListDaoTestSuite {

    @Autowired
    private TaskListDao taskListDao;
    private static final String TASK_LIST_NAME = "Tasks: TODO";
    private static final String TASK_LIST_DESCRIPTION = "Task that are yet to do";

    @Test
    void testTaskListDaoFindByName() {
        //Given
        TaskList taskList = new TaskList(TASK_LIST_NAME, TASK_LIST_DESCRIPTION);
        taskListDao.save(taskList);

        //When
        List<TaskList> findListTasks = taskListDao.findByListName(TASK_LIST_NAME);

        //Then
        assertEquals(1, findListTasks.size());

        //CleanUp
        int id = findListTasks.get(0).getId();
        taskListDao.deleteById(id);
    }
}
