package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "exp_person_category", schema = "exp")
public class ExpPersonCategory extends AuditableEntity {

    private static final String SEQUENCE_NAME = "exp_person_category_sq";

    @Id
    @Column(name = "cat_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
    @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long catId;

    @Column(name = "cat_name", nullable = false)
    private String catName;

    @Column(name = "cat_type", nullable = false,length = 1)
    private String catType;

    @Column(name = "cat_editable", nullable = false)
    private Boolean isEditable;

    @Column(name = "cat_icon")
    private String catIcon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "per_id", referencedColumnName = "per_id")
    private ExpPerson expPerson;

    public ExpPersonCategory() {}
}
