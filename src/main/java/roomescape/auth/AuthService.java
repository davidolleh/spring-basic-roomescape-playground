package roomescape.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import roomescape.member.Member;
import roomescape.member.MemberDao;

@Service
public class AuthService {
    private MemberDao memberDao;

    public AuthService(@Autowired MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Member loginByEmailAndPassword(LoginRequestDto memberLoginRequest) {
        return memberDao.findByEmailAndPassword(memberLoginRequest.email(), memberLoginRequest.password());
    }

    public void loginCheck(Long id, AuthCredential tokenInfo) {
        Member member = memberDao.findById(id);
        String memberName = member.getName();

        if (!tokenInfo.name().equals(memberName)) {
            // TODO:: 예외처리 구체적으로 하기
            throw new RuntimeException("Invalid access token");
        }
    }
}
