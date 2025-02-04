package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "exp_country",schema = "exp")
public class ExpCountry extends AuditableEntity {

    private static final String SEQUENCE_NAME = "exp_country_sq";

    @Id
    @Column(name = "ctr_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long ctrId;

    @Column(name = "ctr_acronym", length = 5,unique = true)
    private String ctrAcronym;

    @Column(name = "ctr_name", length = 200, unique = true)
    private String ctrName;

    @Column(name = "ctr_icon")
    private String ctrIcon;

    public ExpCountry() {}
}
