package org.bcbs.systemcore.lib.auth.provider.client;

import org.bcbs.systemcore.lib.auth.common.constraint.ClientScope;
import org.bcbs.systemcore.lib.auth.common.exception.NoSuchClientException;
import org.bcbs.systemcore.lib.auth.common.utility.CryptoHelper;
import org.bcbs.systemcore.lib.auth.provider.ClientDetails;
import org.bcbs.systemcore.lib.auth.provider.ClientDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class DefaultClientDetailsService implements ClientDetailsService {

    private Map<String, ClientDetails> clientDetailsStore;

    public DefaultClientDetailsService() {
        this.clientDetailsStore = new HashMap<>();
        for (TrustedClient client : TrustedClient.values())
            clientDetailsStore.put(client.name().toLowerCase(), generateClientDetails(client));
    }

    private BaseClientDetails generateClientDetails(TrustedClient client) {
        String clientId = client.name().toLowerCase();
        return new BaseClientDetails(clientId, String.format("{noop}%s", CryptoHelper.doubleMd5Encode(clientId)),
                getScopeByClient(client));
    }

    private Collection<GrantedAuthority> getScopeByClient(TrustedClient client) {
        LinkedHashSet<GrantedAuthority> scopes = new LinkedHashSet<>();
        scopes.add(new SimpleGrantedAuthority(ClientScope.TOKEN.name().toLowerCase()));
        scopes.add(new SimpleGrantedAuthority(ClientScope.PUBLIC_DATA.name().toLowerCase()));

        if (TrustedClient.ADMIN.equals(client))
            scopes.add(new SimpleGrantedAuthority(ClientScope.ADMIN_DATA.name().toLowerCase()));
        else if (TrustedClient.APP.equals(client))
            scopes.add(new SimpleGrantedAuthority(ClientScope.SIGNUP.name().toLowerCase()));

        return scopes;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws NoSuchClientException {
        ClientDetails clientDetails = clientDetailsStore.get(clientId);
        if (clientDetails == null)
            throw new NoSuchClientException(clientId);
        return clientDetails;
    }

    private enum TrustedClient {
        APP, ADMIN
    }
}
