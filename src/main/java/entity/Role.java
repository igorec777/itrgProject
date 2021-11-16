package entity;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "role")
public class Role extends BaseEntity<Long> {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @ManyToMany()
    @JoinTable(
            name = "person_has_role",
            joinColumns = @JoinColumn(name = "roleId"),
            inverseJoinColumns = @JoinColumn(name = "personId")
    )
    private Set<Person> people;

    public Role(String name) {
        this.name = name;
    }

    @Builder
    public Role(Long id, String name) {
        this(name);
        this.id = id;
    }


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
