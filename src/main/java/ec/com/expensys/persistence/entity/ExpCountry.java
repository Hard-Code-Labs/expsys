package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "exp_country",schema = "exp")
public class ExpCountry extends AuditableEntity implements Serializable {

    @Id
    @Column(name = "ctr_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ctrId;

    @Column(name = "ctr_acronym", length = 5,unique = true)
    private String ctrAcronym;

    @Column(name = "ctr_name", length = 200, unique = true)
    private String ctrName;

    @Lob
    @Column(name = "ctr_icon")
    private String ctrIcon;

    public ExpCountry() {}
}
