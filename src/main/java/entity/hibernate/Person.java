package entity.hibernate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import javax.persistence.CascadeType;
import java.util.Set;

@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "person")
public class Person extends BaseEntity<Long> {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "person_has_role",
            joinColumns = @JoinColumn(name = "personId"),
            inverseJoinColumns = @JoinColumn(name = "roleId")
    )
    Set<Role> roles;

    @OneToMany(mappedBy = "client", cascade = CascadeType.REMOVE)
    private Set<Record> clientRecords;

    @OneToMany(mappedBy = "worker", cascade = CascadeType.REMOVE)
    private Set<Record> workerRecords;

    public Person(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public Person(Long id, String name, String surname, String login, String password) {
        this(name, surname, login, password);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + getRolesStr() +
                '}';
    }

    private String getRolesStr() {
        if (roles == null) {
            return null;
        } else if (roles.isEmpty()) {
            return "[]";
        } else {
            StringBuilder sb = new StringBuilder();
            roles.forEach(r -> sb.append(
                    "Role{" +
                            "id=" + r.getId() +
                            ", name='" + r.getName() + '\'' +
                            '}'
            ));
            return sb.toString();
        }
    }

}
