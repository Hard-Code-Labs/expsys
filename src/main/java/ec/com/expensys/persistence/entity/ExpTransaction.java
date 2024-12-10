package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "exp_transaction",schema = "exp")
@SequenceGenerator(name = "exp_transaction_sq", sequenceName = "exp_transaction_sq", allocationSize = 1)
public class ExpTransaction implements Serializable {

    @Id
    @Column(name = "trn_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exp_transaction_sq")
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
