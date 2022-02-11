package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.ErrorMessage;
import java.util.List;

@Repository
public interface ErrorMessageRepository extends JpaRepository<ErrorMessage, Long> {

    List<ErrorMessage> findAllByNodeId(Long id);
    List<ErrorMessage> findAllByUserId(Long id);

}
