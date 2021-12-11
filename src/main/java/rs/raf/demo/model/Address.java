package rs.raf.demo.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Address {
    private String street;
    private String number;
    private String city;
}
