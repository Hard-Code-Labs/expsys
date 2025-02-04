package ec.com.expensys.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.LocalDateTime;

@MappedSuperclass
public class AuditableEntity implements Serializable {

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createAt = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Getter
    @Setter
    @Column(name = "is_delete", nullable = false)
    private Boolean isDeleted = Boolean.FALSE;
}
