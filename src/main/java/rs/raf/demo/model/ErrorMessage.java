package rs.raf.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Getter
@Setter
public class ErrorMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;

    @Enumerated(EnumType.STRING)
    private Operation status;

    @Column
    private Date date;

//    @ManyToOne
//    @JoinColumn(name = "NODE_ID", referencedColumnName = "id")
//    @JsonBackReference
    @Column
    private Long nodeId;

    @ManyToOne
    private User user;

}
