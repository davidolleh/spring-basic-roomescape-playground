package roomescape.auth;

import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.exception.EntityNotFoundException;
import roomescape.exception.InvalidCredentialsException;
import roomescape.member.Member;
import roomescape.member.MemberRepository;

@Service
public class AuthService {
    private final MemberRepository memberRepository;

    public AuthService(@Autowired MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member loginByEmailAndPassword(LoginRequestDto memberLoginRequest) {
        return memberRepository.findByEmailAndPassword(memberLoginRequest.email(), memberLoginRequest.password());
    }

    public void loginCheck(Long id, AuthCredential tokenInfo) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 id의 member는 존재하지 않습니다."));
        String memberName = member.getName();

        if (!tokenInfo.name().equals(memberName)) {
            throw new InvalidCredentialsException("Invalid access token");
        }
    }
}
