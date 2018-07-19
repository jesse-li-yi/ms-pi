package org.bcbs.microservice.account.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.common.constraint.DataView;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "user_role",
        uniqueConstraints = {@UniqueConstraint(name = "uk_name", columnNames = "name")})
public class UserRole extends GenericEntity<Integer> {

    @NotBlank(message = "Invalid name.")
    @Length(max = 16)
    @Column(length = 16, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @Length(max = 64)
    @Column(length = 64)
    @JsonView(value = {DataView.ExtensiveView.class})
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rpKey.role", cascade = {CascadeType.ALL})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<RolePrivilege> rolePrivileges;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", cascade = {CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Account> accounts;

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

    public Set<RolePrivilege> getRolePrivileges() {
        return rolePrivileges;
    }

    public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
