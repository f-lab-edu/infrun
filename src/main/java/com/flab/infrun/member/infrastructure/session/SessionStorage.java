package com.flab.infrun.member.infrastructure.session;

import com.flab.infrun.common.exception.ErrorCode;
import com.flab.infrun.member.domain.exception.InvalidAuthenticationException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class SessionStorage {

    private final Map<String, Session> storage = new ConcurrentHashMap<>();

    public Session store(final String email, final String token) {
        storage.put(email, Session.from(token));

        return storage.get(email);
    }

    public void verifyToken(final String email, final String token) {
        final Session session = storage.get(email);

        tokenValidation(token, session);
    }

    private void tokenValidation(final String token, final Session session) {
        if (session == null || !session.isSameToken(token)) {
            throw new InvalidAuthenticationException(ErrorCode.INVALID_AUTHENTICATION);
        }
    }
}
