package org.bcbs.microservice.account.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.common.constraint.DataView;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "auth_role")
public class AuthRole extends GenericEntity<Integer> {

    @NotBlank(message = "Invalid name.")
    @Length(max = 16)
    @Column(length = 16, nullable = false, unique = true)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @Length(max = 64)
    @Column(length = 64)
    @JsonView(value = {DataView.ExtensiveView.class})
    private String description;

    /*@ManyToMany(fetch = FetchType.EAGER, mappedBy = "students", cascade = {CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<AuthPrivilege> ;*/

    // Getter & setter.
}
