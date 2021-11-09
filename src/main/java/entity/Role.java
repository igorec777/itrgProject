package entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @ManyToMany(fetch = FetchType.LAZY)
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
                //", people=" + getPeopleStr() +
                '}';
    }

//    private String getPeopleStr() {
//        if (people == null) {
//            return "null";
//        } else if (people.isEmpty()) {
//            return "[]";
//        } else {
//            StringBuilder sb = new StringBuilder();
//            people.forEach(p -> sb.append("Person{" +
//                    "id=" + p.getId() +
//                    ", name='" + p.getName() + '\'' +
//                    ", surname='" + p.getSurname() + '\'' +
//                    ", login='" + p.getLogin() + '\'' +
//                    ", password='" + p.getPassword() + '\'' +
//                    '}'));
//            return sb.toString();
//        }
//    }
}
