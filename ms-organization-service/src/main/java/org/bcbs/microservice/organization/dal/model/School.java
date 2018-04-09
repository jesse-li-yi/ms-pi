package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.dal.view.PublicDataView;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "school")
public class School extends GenericEntity<Integer> {

    @NotEmpty
    @Length(max = 16)
    @Column(length = 16, nullable = false)
    @JsonView(PublicDataView.class)
    private String sn;

    @NotEmpty
    @Length(max = 32)
    @Column(length = 32, nullable = false)
    @JsonView(PublicDataView.class)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(PublicDataView.class)
    private Institution institution;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "schools", cascade = {CascadeType.MERGE})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<President> presidents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "school")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Clazz> clazzes;
}
