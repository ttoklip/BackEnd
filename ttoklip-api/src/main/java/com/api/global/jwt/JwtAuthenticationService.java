package com.api.global.jwt;

import com.common.jwt.TokenProvider;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationService {

    private final TokenProvider tokenProvider;
    private final MemberService memberService;
    private static final String ROLE_PREFIX = "ROLE_";

    public void authenticate(String token) {
        if (tokenProvider.validate(token)) {
            String email = tokenProvider.extract(token);
            var member = memberService.findByEmail(email);
            setSecurityContext(member, token);
        }
    }

    private void setSecurityContext(final Object member, final String token) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(ROLE_PREFIX + member.getRole().name())
        );
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(member, token, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
