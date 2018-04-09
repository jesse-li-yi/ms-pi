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
@Table(name = "institution")
public class Institution extends GenericEntity<Integer> {

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "institution")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<President> presidents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "institution")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<School> schools;
}
