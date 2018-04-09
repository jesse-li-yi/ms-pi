package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bcbs.microservice.dal.model.Account;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.dal.view.PublicDataView;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Set;

@Data()
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "president")
public class President extends GenericEntity<Integer> {

    @Embedded
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(PublicDataView.class)
    private Institution institution;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "president_school", joinColumns = {@JoinColumn(name = "president_id")},
            inverseJoinColumns = {@JoinColumn(name = "school_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<School> schools;
}
