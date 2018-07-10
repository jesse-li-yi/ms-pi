package org.bcbs.systemcore.lib.auth.provider;

import org.bcbs.systemcore.lib.auth.common.exception.NoSuchClientException;

public interface ClientDetailsService {

    ClientDetails loadClientByClientId(String clientId) throws NoSuchClientException;
}
