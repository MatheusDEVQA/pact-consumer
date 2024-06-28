package br.ce.wcaquino.consumer.tasks.pact;

import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.Rule;

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

}
