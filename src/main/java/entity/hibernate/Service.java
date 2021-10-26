package entity.hibernate;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.Set;


@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "service")
public class Service extends BaseEntity<Long> {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private double price;

    @OneToMany(mappedBy = "service", cascade = CascadeType.REMOVE)
    private Set<Record> records;

    public Service(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
