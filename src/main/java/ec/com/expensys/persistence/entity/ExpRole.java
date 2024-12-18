package ec.com.expensys.persistence.entity;

import ec.com.expensys.web.utils.RoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "exp_role",schema = "exp")
public class ExpRole extends AuditableEntity {

    private final static String SEQUENCE_NAME = "exp_role_seq";

    @Id
    @Column(name = "rol_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long rolId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol_name", nullable = false, length = 50)
    private RoleEnum rolName;

    @OneToMany(mappedBy = "expRole",fetch = FetchType.LAZY)
    private List<ExpRolePerson> roles;

    @OneToMany(mappedBy =  "expRole", fetch = FetchType.EAGER)
    private List<ExpRolePermission> permissions;

}
