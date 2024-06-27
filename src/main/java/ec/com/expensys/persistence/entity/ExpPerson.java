package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "exp_person", schema = "exp")
@SequenceGenerator(name = "exp_person_sq", sequenceName = "exp_person_sq", allocationSize = 1)
public class ExpPerson extends AuditableEntity implements Serializable {

    @Id
    @Column(name = "per_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exp_person_sq")
    private Long perId;

    @Column(name = "per_uuid", nullable = false, unique = true)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID perUUID;

    @Column(name = "per_mail", nullable = false, length = 100, unique = true)
    private String perMail;

    @Column(name = "per_name", nullable = false, length = 60)
    private String perName;

    @Column(name = "per_lastname",nullable = false, length = 60)
    private String perLastname;

    @Column(name = "per_password",nullable = false, length = 255)
    private String perPassword;

    @Column(name = "per_avatar")
    private byte[] perAvatar;

    @Column(name = "per_last_access", columnDefinition = "timestamp")
    private LocalDateTime lastAccess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ctr_id", referencedColumnName = "ctr_id")
    private ExpCountry expCountry;

    @OneToMany(mappedBy = "expPerson")
    private List<ExpRolePerson> roleList;

    @OneToMany(mappedBy = "expPerson")
    private List<ExpPersonCategory> expCategories;

    @OneToMany(mappedBy = "expPerson",fetch = FetchType.EAGER)
    private List<ExpTransaction> expTransactions;

    public ExpPerson() {}
}
