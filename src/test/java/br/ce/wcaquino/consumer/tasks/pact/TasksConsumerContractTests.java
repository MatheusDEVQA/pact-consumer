package br.ce.wcaquino.consumer.tasks.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import br.ce.wcaquino.consumer.tasks.model.Task;
import br.ce.wcaquino.consumer.tasks.service.TasksConsumer;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.internal.matchers.Matches;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TasksConsumerContractTests {

    @Rule
    public PactProviderRule mockProvider = new PactProviderRule("Tasks", this);

    @Pact(consumer = "BasicConsumer")
    public RequestResponsePact createPact(PactDslWithProvider builder){
        return builder
            .given("There is a task with id = 1")
            .uponReceiving("Retrieve Task #1")
                .path("/todo/1")
                .method("GET")
            .willRespondWith()
                .status(200)
                .body("{\"id\": 1, \"task\": \"Task from pact\", \"dueDate\": \"2024/01/02\"}")
                .toPact();
    }
    
    @Test
    @PactVerification
    public void test() throws IOException {

        TasksConsumer consumer = new TasksConsumer(mockProvider.getUrl());

        Task task = consumer.getTask(1L);

        assertThat(task.getId(), is(1L));
        assertThat(task.getTask(), is("Task from pact"));


    }

}
