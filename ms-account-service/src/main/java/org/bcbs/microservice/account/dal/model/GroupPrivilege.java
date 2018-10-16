package org.bcbs.microservice.account.dal.model;

import org.bcbs.microservice.account.dal.model.embeddable.GroupPrivilegeKey;
import org.bcbs.microservice.common.constraint.PrivilegeOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "group_privilege",
        indexes = {@Index(name = "fk_gp_group_id", columnList = "group_id"),
                @Index(name = "fk_gp_privilege_id", columnList = "privilege_id")})
public class GroupPrivilege implements Serializable {

    @EmbeddedId
    private GroupPrivilegeKey gpKey;

    @NotNull(message = "Invalid privilege option.")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "opt", length = 6, nullable = false)
    private PrivilegeOption option;

    // Getter & setter.
    public GroupPrivilegeKey getGpKey() {
        return gpKey;
    }

    public void setGpKey(GroupPrivilegeKey gpKey) {
        this.gpKey = gpKey;
    }

    public PrivilegeOption getOption() {
        return option;
    }

    public void setOption(PrivilegeOption option) {
        this.option = option;
    }
}
