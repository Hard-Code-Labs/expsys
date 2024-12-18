package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "exp_transaction",schema = "exp")

public class ExpTransaction {

    private static final String SEQUENCE_NAME = "exp_transaction_sq";

    @Id
    @Column(name = "trn_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long trnId;

    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "trn_uuid", nullable = false, unique = true)
    private UUID trnUUID;

    @Column(name = "trn_date", nullable = false)
    private LocalDateTime trnDate;

    @Column(name = "trn_month_reference", nullable = false)
    private Integer trnMonthReference;

    @Column(name = "trn_description", nullable = false)
    private String trnDescription;

    @Column(name = "trn_amount", nullable = false, precision = 10, scale = 3)
    private BigDecimal trnAmount;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cat_id",referencedColumnName = "cat_id")
    private ExpPersonCategory expCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "per_id",referencedColumnName = "per_id")
    private ExpPerson expPerson;

    public ExpTransaction() {}
}
