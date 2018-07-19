package org.bcbs.microservice.account.dal.model;

import org.bcbs.microservice.account.dal.model.embeddable.RolePrivilegeKey;
import org.bcbs.microservice.common.constraint.PrivilegeOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "role_privilege",
        indexes = {@Index(name = "fk_rp_role_id", columnList = "role_id"),
                @Index(name = "fk_rp_privilege_id", columnList = "privilege_id")})
public class RolePrivilege {

    @EmbeddedId
    private RolePrivilegeKey rpKey;

    @NotNull(message = "Invalid privilege option.")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "opt", length = 6, nullable = false)
    private PrivilegeOption option;

    // Getter & setter.
    public RolePrivilegeKey getRpKey() {
        return rpKey;
    }

    public void setRpKey(RolePrivilegeKey rpKey) {
        this.rpKey = rpKey;
    }

    public PrivilegeOption getOption() {
        return option;
    }

    public void setOption(PrivilegeOption option) {
        this.option = option;
    }
}
