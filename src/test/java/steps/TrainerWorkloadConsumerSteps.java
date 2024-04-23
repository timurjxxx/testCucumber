package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gypApp_workLoadService.activemq.TrainerWorkloadConsumer;
import com.gypApp_workLoadService.dto.ActionType;
import com.gypApp_workLoadService.dto.TrainerWorkloadRequest;
import com.gypApp_workLoadService.service.TrainerWorkloadService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jms.core.JmsTemplate;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TrainerWorkloadConsumerSteps {

    @Mock
    private TrainerWorkloadService trainerWorkloadService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private TrainerWorkloadConsumer trainerWorkloadConsumer;

    private String jsonRequest;
    private TrainerWorkloadRequest request = new TrainerWorkloadRequest();

    public TrainerWorkloadConsumerSteps() {
        MockitoAnnotations.openMocks(this); // Initialize annotated mocks
    }

    @Given("a valid trainer workload message")
    public void a_valid_trainer_workload_message() throws JsonProcessingException {
        request = TrainerWorkloadRequest.builder()
                .trainerFirstname("test")
                .trainerLastname("test")
                .trainerUsername("testUsername")
                .trainingDate(LocalDate.now())
                .isActive(true)
                .trainingDuration(90)
                .type(ActionType.ADD)
                .build();
        jsonRequest = "{}"; // Replace with actual JSON representation of request
        when(objectMapper.readValue(jsonRequest, TrainerWorkloadRequest.class)).thenReturn(request);
    }

    @Given("an invalid trainer workload message")
    public void an_invalid_trainer_workload_message() throws JsonProcessingException {
        request = TrainerWorkloadRequest.builder()
                .trainerFirstname("test")
                .trainerLastname("test")
                .trainerUsername("testUsername")
                .trainingDate(LocalDate.now())
                .isActive(true)
                .trainingDuration(90)
                .type(ActionType.DELETE)
                .build();
        jsonRequest = "{}"; // Replace with actual JSON representation of request
        when(objectMapper.readValue(jsonRequest, TrainerWorkloadRequest.class)).thenReturn(request);
    }

    @When("the message is received by the consumer")
    public void the_message_is_received_by_the_consumer() throws JsonProcessingException {
        trainerWorkloadConsumer.receiveMessage(jsonRequest);
    }

    @Then("the workload should be updated")
    public void the_workload_should_be_updated() {
        verify(trainerWorkloadService).updateWorkload(any(TrainerWorkloadRequest.class));
    }

    @And("the message should not be sent to the dead-letter queue")
    public void the_message_should_not_be_sent_to_the_dead_letter_queue() {
        verify(jmsTemplate, never()).convertAndSend(anyString(), anyString());
    }

    @And("the message should be sent to the dead-letter queue")
    public void the_message_should_be_sent_to_the_dead_letter_queue() throws JsonProcessingException {

        trainerWorkloadConsumer.receiveMessage(jsonRequest);
    }

    @Given("a bad trainer workload message")
    public void aBadTrainerWorkloadMessage() {

    }

    @Then("the message should be rejected")
    public void theMessageShouldBeRejected() {

    }

    @And("an error should be logged")
    public void anErrorShouldBeLogged() {
    }
}
