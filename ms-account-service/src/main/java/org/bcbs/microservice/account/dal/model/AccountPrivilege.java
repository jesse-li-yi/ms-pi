package org.bcbs.microservice.account.dal.model;

import org.bcbs.microservice.account.dal.model.embeddable.AccountPrivilegeKey;
import org.bcbs.microservice.common.constraint.PrivilegeOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "account_privilege",
        indexes = {@Index(name = "fk_ap_account_id", columnList = "account_id"),
                @Index(name = "fk_ap_privilege_id", columnList = "privilege_id")})
public class AccountPrivilege implements Serializable {

    @EmbeddedId
    private AccountPrivilegeKey apKey;

    @NotNull(message = "Invalid privilege option.")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "opt", length = 6, nullable = false)
    private PrivilegeOption option;

    // Getter & setter.
    public AccountPrivilegeKey getApKey() {
        return apKey;
    }

    public void setApKey(AccountPrivilegeKey apKey) {
        this.apKey = apKey;
    }

    public PrivilegeOption getOption() {
        return option;
    }

    public void setOption(PrivilegeOption option) {
        this.option = option;
    }
}
