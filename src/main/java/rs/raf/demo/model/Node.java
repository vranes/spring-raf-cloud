package rs.raf.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
public class Node {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Status status;

    @ManyToOne
    private User createdBy;

    @Column
    private Boolean active;

    @Column
    private String name;
}
