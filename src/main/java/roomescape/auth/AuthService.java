package roomescape.auth;

import org.springframework.stereotype.Service;
import roomescape.member.Member;
import roomescape.member.MemberDao;

@Service
public class AuthService {
    private MemberDao memberDao;

    public AuthService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public Member login(LoginRequest memberLoginRequest) {
        return memberDao.findByEmailAndPassword(memberLoginRequest.email(), memberLoginRequest.password());
    }

    public Member loginCheck(Long id) {
        return memberDao.findById(id);
    }
}
