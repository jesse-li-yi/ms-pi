package org.bcbs.microservice.account.dal.model.embeddable;

import org.bcbs.microservice.account.dal.model.Account;
import org.bcbs.microservice.account.dal.model.UserPrivilege;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class AccountPrivilegeKey implements Serializable {

    @NotNull(message = "Invalid account.")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id", foreignKey = @ForeignKey(name = "fk_ap_account_id"))
    private Account account;

    @NotNull(message = "Invalid account privilege.")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "privilege_id", foreignKey = @ForeignKey(name = "fk_ap_privilege_id"))
    private UserPrivilege privilege;

    @Override
    public int hashCode() {
        return this.account.getPhoneNo().hashCode() * 31 + this.privilege.getCode().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof GroupPrivilegeKey && obj.hashCode() == this.hashCode();
    }

    // Getter & setter.
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }
}
