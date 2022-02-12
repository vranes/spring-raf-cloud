package rs.raf.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    private User user;

    @Column
    private Boolean active;

    @Column
    private String name;

//    @Version
//    private int version;

    @Column(name = "created_at")     // TODO
    private Date createdAt;
}
