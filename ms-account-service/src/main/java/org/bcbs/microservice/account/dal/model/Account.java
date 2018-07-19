package org.bcbs.microservice.account.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.common.constraint.DataView;
import org.bcbs.microservice.common.constraint.RegexPattern;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "account",
        uniqueConstraints = {@UniqueConstraint(name = "uk_phone_no", columnNames = "phoneNo")})
public class Account extends GenericEntity<Integer> {

    @Pattern(regexp = RegexPattern.PHONE_NO, message = "Invalid phone number.")
    @Column(length = 11, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String phoneNo;

    @NotBlank(message = "Invalid password.")
    @Length(max = 64)
    @Column(length = 64, nullable = false)
    @JsonView(value = {DataView.ExtensiveView.class})
    private String password;

    @NotNull(message = "Invalid active status.")
    @Column(columnDefinition = "bit not null default true")
    @JsonView(value = {DataView.ExtensiveView.class})
    private Boolean isActive;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "apKey.account", cascade = {CascadeType.ALL})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<AccountPrivilege> accountPrivileges;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "account_role",
            joinColumns = {@JoinColumn(name = "account_id",
                    foreignKey = @ForeignKey(name = "fk_ar_account_id"))},
            inverseJoinColumns = {@JoinColumn(name = "role_id",
                    foreignKey = @ForeignKey(name = "fk_ar_role_id"))},
            indexes = {@Index(name = "fk_ar_account_id", columnList = "account_id"),
                    @Index(name = "fk_ar_role_id", columnList = "role_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<UserRole> roles;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "account_group",
            joinColumns = {@JoinColumn(name = "account_id",
                    foreignKey = @ForeignKey(name = "fk_ag_account_id"))},
            inverseJoinColumns = {@JoinColumn(name = "group_id",
                    foreignKey = @ForeignKey(name = "fk_ag_group_id"))},
            indexes = {@Index(name = "fk_ag_account_id", columnList = "account_id"),
                    @Index(name = "fk_ag_group_id", columnList = "group_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<UserGroup> groups;

    // Getter & setter.
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Set<AccountPrivilege> getAccountPrivileges() {
        return accountPrivileges;
    }

    public void setAccountPrivileges(Set<AccountPrivilege> accountPrivileges) {
        this.accountPrivileges = accountPrivileges;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public Set<UserGroup> getGroups() {
        return groups;
    }

    public void setGroups(Set<UserGroup> groups) {
        this.groups = groups;
    }
}
