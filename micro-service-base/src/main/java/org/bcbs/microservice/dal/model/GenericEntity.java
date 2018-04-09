package org.bcbs.microservice.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.bcbs.microservice.dal.view.PublicDataView;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public abstract class GenericEntity<I> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private I id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @JsonView(PublicDataView.class)
    private Date createTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(PublicDataView.class)
    private Date modifyTime;

    @NotNull
    @Column(nullable = false, columnDefinition = "bit default false")
    @JsonView(PublicDataView.class)
    private Boolean isDeleted = Boolean.FALSE;
}
