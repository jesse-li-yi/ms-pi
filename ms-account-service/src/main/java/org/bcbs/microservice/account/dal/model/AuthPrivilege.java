package org.bcbs.microservice.account.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.common.constraint.DataView;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "auth_privilege")
public class AuthPrivilege extends GenericEntity<Integer> implements GrantedAuthority {

    @NotBlank(message = "Invalid name.")
    @Length(max = 16)
    @Column(length = 16, nullable = false, unique = true)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @Length(max = 64)
    @Column(length = 64)
    @JsonView(value = {DataView.ExtensiveView.class})
    private String description;

    @Length(max = 32)
    @Column(length = 32, nullable = false, unique = true)
    @JsonView(value = {DataView.ExtensiveView.class})
    private String code;

    @Override
    public String getAuthority() {
        return this.code;
    }

    // Getter & setter.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
