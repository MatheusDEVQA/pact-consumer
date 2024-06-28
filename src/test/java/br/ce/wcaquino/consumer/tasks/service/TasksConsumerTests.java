package br.ce.wcaquino.consumer.tasks.service;

import br.ce.wcaquino.consumer.tasks.model.Task;
import br.ce.wcaquino.consumer.utils.RequestHelper;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class TasksConsumerTests {

    private static final String INVALID_URL = "http://invalidUrl.com";

    @InjectMocks
    private TasksConsumer consumer = new TasksConsumer(INVALID_URL);

    @Mock
    private RequestHelper helper;

    @Test
    public void shouldBeNotNull() throws  IOException {

        Map<String, String> expectedTask = new HashMap<String, String>();
        expectedTask.put("id", "3");
        expectedTask.put("task", "task mocked");
        expectedTask.put("dueDate", "2024-06-02");

        Mockito
                .when(helper.get(INVALID_URL + "/todo/1"))
                .thenReturn(expectedTask);

        Task task = consumer.getTask(1L);

        Assert.assertNotNull(task);
        Assert.assertThat (task.getId(), CoreMatchers.is(1L));
    }
}
