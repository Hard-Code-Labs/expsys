package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "exp_permission", schema = "exp")
public class ExpPermission extends AuditableEntity {

    private static final String SEQUENCE_NAME = "exp_permission_sq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    @Column(name = "prm_id", nullable = false)
    private Long prmId;

    @NotNull
    @Column(name = "prm_name", nullable = false, length = Integer.MAX_VALUE)
    private String prmName;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
    private List<ExpRolePermission> rolePermissions;
}