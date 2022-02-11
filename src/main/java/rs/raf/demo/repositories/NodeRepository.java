package rs.raf.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.raf.demo.model.Node;
import rs.raf.demo.model.Status;
import rs.raf.demo.model.User;

import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
    public List<Node> findAllByUserId (Long userId);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    public Optional<Node> findByIdAndUserId (Long id, Long userId);

    public Node findByNameAndUserId (String name, Long userId);


    @Query("select n from Node n where" +
            " ((n.name like :name or :name is null) and" +
            " ((n.createdAt between :dateFrom and :dateTo) or (:dateFrom is null and :dateTo is null)) and" +
            " (n.status in (:status_list))) and" +
            " (n.user.id = :userId) and n.active = true")
    List<Node> searchNodes(@Param("name") String name,
                                 @Param("dateFrom") Date dateFrom,
                                 @Param("dateTo") Date dateTo,
                                 @Param("status_list") List<Status> statusList,
                                 @Param("userId") Long userId
    ); // TODO
}
