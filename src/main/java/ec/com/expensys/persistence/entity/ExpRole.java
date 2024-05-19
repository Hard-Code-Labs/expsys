package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "exp_role",schema = "exp")
public class ExpRole extends AuditableEntity implements Serializable {

    @Id
    @Column(name = "rol_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rolId;

    @Column(name = "rol_name", nullable = false, length = 50)
    private String rolName;

    @OneToMany(mappedBy = "expRole")//Va el nombre del objeto mapeado en Java. No en la BD
    private List<ExpRolePerson> roles;

    public ExpRole() {}
}
