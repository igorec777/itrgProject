package entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@Table(name = "service")
public class Service extends BaseEntity<Long> {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

    @Builder
    public Service(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
