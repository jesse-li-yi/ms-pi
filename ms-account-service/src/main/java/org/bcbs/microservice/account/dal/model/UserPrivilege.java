package org.bcbs.microservice.account.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.common.constraint.DataView;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "user_privilege",
        uniqueConstraints = {@UniqueConstraint(name = "uk_name", columnNames = "name"),
                @UniqueConstraint(name = "uk_code", columnNames = "code")})
public class UserPrivilege extends GenericEntity<Integer> implements GrantedAuthority {

    @NotBlank(message = "Invalid name.")
    @Length(max = 16)
    @Column(length = 16, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @Length(max = 64)
    @Column(length = 64)
    @JsonView(value = {DataView.ExtensiveView.class})
    private String description;

    @Length(max = 32)
    @Column(length = 32, nullable = false)
    @JsonView(value = {DataView.ExtensiveView.class})
    private String code;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apKey.privilege", cascade = {CascadeType.ALL})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<AccountPrivilege> accountPrivileges;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "gpKey.privilege", cascade = {CascadeType.ALL})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<GroupPrivilege> groupPrivileges;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "rpKey.privilege", cascade = {CascadeType.ALL})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<RolePrivilege> rolePrivileges;

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

    public Set<AccountPrivilege> getAccountPrivileges() {
        return accountPrivileges;
    }

    public void setAccountPrivileges(Set<AccountPrivilege> accountPrivileges) {
        this.accountPrivileges = accountPrivileges;
    }

    public Set<GroupPrivilege> getGroupPrivileges() {
        return groupPrivileges;
    }

    public void setGroupPrivileges(Set<GroupPrivilege> groupPrivileges) {
        this.groupPrivileges = groupPrivileges;
    }

    public Set<RolePrivilege> getRolePrivileges() {
        return rolePrivileges;
    }

    public void setRolePrivileges(Set<RolePrivilege> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }
}
