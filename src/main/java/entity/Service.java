package entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "service")
public class Service extends BaseEntity<Long> {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Double price;

//    @OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
//    private Set<Record> records = new HashSet<>();

    @Builder
    public Service(Long id, String name, Double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
