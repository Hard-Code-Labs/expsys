package ec.com.expensys.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "exp_person_category", schema = "exp")
public class ExpPersonCategory extends AuditableEntity implements Serializable {

    @Id
    @Column(name = "cat_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer catId;

    @Column(name = "cat_name", nullable = false)
    private String catName;

    @Column(name = "cat_type", nullable = false,length = 1)
    private String catType;

    @Column(name = "cat_editable", nullable = false)
    private Boolean isEditable;

    @Lob
    @Column(name = "cat_icon")
    private String catIcon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "per_id", referencedColumnName = "per_id")
    private ExpPerson expPerson;

    public ExpPersonCategory() {}
}
