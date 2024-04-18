package steps;


import com.gypApp_workLoadService.dao.TrainerWorkloadDAO;
import com.gypApp_workLoadService.dto.ActionType;
import com.gypApp_workLoadService.dto.TrainerWorkloadRequest;
import com.gypApp_workLoadService.model.Trainer;
import com.gypApp_workLoadService.service.TrainerWorkloadService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TrainerWorkloadServiceSteps {

    @Mock
    private TrainerWorkloadDAO trainerWorkloadDAO;

    @InjectMocks
    private TrainerWorkloadService trainerWorkloadService;

    private TrainerWorkloadRequest request;

    public TrainerWorkloadServiceSteps() {
        MockitoAnnotations.openMocks(this); // Initialize annotated mocks
    }

    @Given("a new trainer with the username {string}")
    public void a_new_trainer_with_the_username(String username) {
        request = TrainerWorkloadRequest.builder()
                .trainerFirstname("John")
                .trainerLastname("Doe")
                .trainerUsername(username)
                .trainingDate(LocalDate.now())
                .isActive(true)
                .trainingDuration(60)
                .type(ActionType.ADD)
                .build();
    }

    @Given("an existing trainer with the username {string}")
    public void an_existing_trainer_with_the_username(String username) {
        Map<String, Integer> trainingSummaryDuration = new HashMap<>();
        trainingSummaryDuration.put("4:2024", 90); // Assuming there's already some training duration recorded for April 2024
        Trainer existingTrainer = Trainer.builder()
                .firstName("Existing")
                .lastName("Trainer")
                .username(username)
                .isActive(true)
                .trainingSummaryDuration(trainingSummaryDuration)
                .build();
        when(trainerWorkloadDAO.findTrainerByUsername(username)).thenReturn(existingTrainer);
    }

    @When("a training duration of {int} minutes is added for the current date")
    public void a_training_duration_of_minutes_is_added_for_the_current_date(int duration) {
        request.setTrainingDuration(duration);
        trainerWorkloadService.updateWorkload(request);
    }

    @Then("the trainer's workload should be updated")
    public void the_trainer_s_workload_should_be_updated() {
        verify(trainerWorkloadDAO).save(any(Trainer.class));
    }

    @Then("the trainer should be saved to the database")
    public void the_trainer_should_be_saved_to_the_database() {
        verify(trainerWorkloadDAO).save(any(Trainer.class));
    }
}
