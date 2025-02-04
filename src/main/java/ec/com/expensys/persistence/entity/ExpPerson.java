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
public class ExpPerson extends AuditableEntity {

    private static final String SEQUENCE_NAME = "exp_person_sq";

    @Id
    @Column(name = "per_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long perId;

    @Column(name = "per_uuid", nullable = false, unique = true)
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID perUUID;

    @Column(name = "per_mail", nullable = false, length = 100, unique = true)
    private String perMail;

    @Column(name = "per_name", nullable = false, length = 30)
    private String perName;

    @Column(name = "per_lastname",nullable = false, length = 30)
    private String perLastname;

    @Column(name = "per_password",nullable = false, length = 60)
    private String perPassword;

    @Column(name = "per_avatar")
    private byte[] perAvatar;

    @Column(name = "per_last_access", columnDefinition = "timestamp")
    private LocalDateTime lastAccess;

    @Column(name = "per_verification_code", length = 100)
    private String perVerificationCode;

    @Column(name = "per_enabled", nullable = false)
    private Boolean isEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ctr_id", referencedColumnName = "ctr_id")
    private ExpCountry expCountry;

    @OneToMany(mappedBy = "expPerson", fetch = FetchType.EAGER)
    private List<ExpRolePerson> roleList;

    @OneToMany(mappedBy = "expPerson")
    private List<ExpPersonCategory> expCategories;

    @OneToMany(mappedBy = "expPerson",fetch = FetchType.LAZY)
    private List<ExpTransaction> expTransactions;

    public ExpPerson() {}
}
