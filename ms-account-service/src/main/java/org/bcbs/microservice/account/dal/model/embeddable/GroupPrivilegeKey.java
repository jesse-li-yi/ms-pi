package org.bcbs.microservice.account.dal.model.embeddable;

import org.bcbs.microservice.account.dal.model.UserGroup;
import org.bcbs.microservice.account.dal.model.UserPrivilege;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class GroupPrivilegeKey implements Serializable {

    @NotNull(message = "Invalid group.")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_gp_group_id"))
    private UserGroup group;

    @NotNull(message = "Invalid group privilege.")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "privilege_id", foreignKey = @ForeignKey(name = "fk_gp_privilege_id"))
    private UserPrivilege privilege;

    @Override
    public int hashCode() {
        return this.group.getName().hashCode() * 31 + this.privilege.getCode().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GroupPrivilegeKey && obj.hashCode() == this.hashCode();
    }

    // Getter & setter.
    public UserGroup getGroup() {
        return group;
    }

    public void setGroup(UserGroup group) {
        this.group = group;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }
}
