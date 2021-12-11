package rs.raf.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "STUD")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "ST_STREET")),
            @AttributeOverride(name = "number", column = @Column(name = "ST_NUMBER")),
            @AttributeOverride(name = "city", column = @Column(name = "ST_CITY"))
    })
    private Address address;

    @ManyToMany(mappedBy = "students", fetch = FetchType.LAZY, cascade = {  CascadeType.ALL })
    private List<Course> courses = new ArrayList<>();
}
