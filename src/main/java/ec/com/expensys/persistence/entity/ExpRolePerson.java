package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "exp_role_person", schema = "exp")
public class ExpRolePerson extends AuditableEntity implements Serializable {

    @Id
    @Column(name = "rop_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ropId;

    @Column(name = "rop_active", nullable = false)
    private Boolean ropActive;

    @Column(name = "rop_start_date", nullable = false, columnDefinition = "timestamp")
    private LocalDate ropStartDate;

    @Column(name = "rop_end_date", columnDefinition = "timestamp")
    private LocalDate ropEndDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", referencedColumnName = "rol_id")
    private ExpRole expRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "per_id", referencedColumnName = "per_id")
    private ExpPerson expPerson;

    public ExpRolePerson() {}
}
