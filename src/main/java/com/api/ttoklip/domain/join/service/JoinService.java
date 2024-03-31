package com.api.ttoklip.domain.join.service;

import com.api.ttoklip.domain.join.dto.JoinRequest;
import com.api.ttoklip.domain.member.domain.Member;
import com.api.ttoklip.domain.member.domain.Role;
import com.api.ttoklip.domain.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinRequest joinRequest) {

        String joinId = joinRequest.getJoinId();
        String password = joinRequest.getPassword();

        Boolean isExist = memberRepository.existsByJoinId(joinId);

        if (isExist) {

            return;
        }

        Member data = Member.builder()
                .joinId(joinId)
                .password(bCryptPasswordEncoder.encode(password))
                .role(Role.CLIENT)
                .build();

        memberRepository.save(data);
    }


}
