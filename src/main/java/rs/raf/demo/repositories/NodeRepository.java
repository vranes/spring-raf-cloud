package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.Node;
import rs.raf.demo.model.User;

import java.util.List;
import java.util.Optional;


@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
    public List<Node> findAllByCreatedBy (User user);
    public Optional<Node> findByIdAndCreatedBy (Long id, User user);
}
