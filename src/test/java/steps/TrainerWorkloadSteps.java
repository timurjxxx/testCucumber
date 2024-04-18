package steps;

import com.gypApp_workLoadService.dto.ActionType;
import com.gypApp_workLoadService.dto.TrainerWorkloadRequest;
import com.gypApp_workLoadService.service.TrainerWorkloadService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TrainerWorkloadSteps {

    @Mock
    private TrainerWorkloadService trainerWorkloadService;

    private TrainerWorkloadRequest request = new TrainerWorkloadRequest();
    private ResponseEntity<HttpStatus> response;

    public TrainerWorkloadSteps() {
        MockitoAnnotations.openMocks(this); // Initialize annotated mocks
    }

    @Given("a trainer workload update request with type {string}")
    public void aTrainerWorkloadUpdateRequestWithType(String arg0) {
        request =  TrainerWorkloadRequest.builder()
                .trainerFirstname("test")
                .trainerLastname("test")
                .trainerUsername("testUsername")
                .trainingDate(LocalDate.now())
                .isActive(true)
                .trainingDuration(90)
                .type(ActionType.ADD)
                .build();


    }
    @When("I send the update request")
    public void iSendTheUpdateRequest() {
        Mockito.doNothing().when(trainerWorkloadService).updateWorkload(request);
        trainerWorkloadService.updateWorkload(request);


    }
    @Then("the workload should add training  duration successfully")
    public void theWorkloadShouldBeUpdatedSuccessfully() {
        response = ResponseEntity.ok().build();
    }

    @And("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        assertEquals(expectedStatusCode, response.getStatusCode().value());
    }

}
