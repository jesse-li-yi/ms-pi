package org.bcbs.microservice.account.dal.model.embeddable;

import org.bcbs.microservice.account.dal.model.UserPrivilege;
import org.bcbs.microservice.account.dal.model.UserRole;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class RolePrivilegeKey implements Serializable {

    @NotNull(message = "Invalid role.")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", foreignKey = @ForeignKey(name = "fk_rp_role_id"))
    private UserRole role;

    @NotNull(message = "Invalid role privilege.")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "privilege_id", foreignKey = @ForeignKey(name = "fk_rp_privilege_id"))
    private UserPrivilege privilege;

    @Override
    public int hashCode() {
        return this.role.getName().hashCode() * 31 + this.privilege.getCode().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GroupPrivilegeKey && obj.hashCode() == this.hashCode();
    }

    // Getter & setter.
    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }
}
