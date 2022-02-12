package rs.raf.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.raf.demo.model.ErrorMessage;
import rs.raf.demo.model.Node;
import rs.raf.demo.model.Operation;
import rs.raf.demo.model.User;
import rs.raf.demo.repositories.ErrorMessageRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ErrorMessageService {

    private ErrorMessageRepository errorMessageRepository;

    @Autowired
    public ErrorMessageService(ErrorMessageRepository errorMessageRepository) {
        this.errorMessageRepository = errorMessageRepository;
    }

    public void saveErrorMessage(Operation operation, Long nodeId, User user) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setNodeId(nodeId);
        errorMessage.setUser(user);
        errorMessage.setDate(new Date(Calendar.getInstance().getTime().getTime()));
        switch (operation) {
            case START:
                errorMessage.setStatus(Operation.START);
                errorMessage.setMessage("Failed to start node");
                errorMessageRepository.save(errorMessage);
            case STOP:
                errorMessage.setStatus(Operation.STOP);
                errorMessage.setMessage("Failed to stop machine");
                errorMessageRepository.save(errorMessage);
            case RESTART:
                errorMessage.setStatus(Operation.RESTART);
                errorMessage.setMessage("Failed to restart machine");
                errorMessageRepository.save(errorMessage);
            default:

        }
    }

    public List<ErrorMessage> findAllByUser(User user){
        return errorMessageRepository.findAllByUserId(user.getId());
    }
}
