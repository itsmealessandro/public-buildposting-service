import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class PrintCancelDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        try {
            String requestId = (String) execution.getVariable("requestId");
            String billingInfo = (String) execution.getVariable("billingInfo");
            
            Files.write(
                Paths.get("orders.txt"),
                (requestId + "," + billingInfo + "\n").getBytes(),
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}