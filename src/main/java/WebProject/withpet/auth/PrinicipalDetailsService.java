package WebProject.withpet.auth;

import WebProject.withpet.users.domain.User;
import WebProject.withpet.users.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrinicipalDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    // TODO : UsernameNotFoundException 커스텀 에러로 변경
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        return new PrincipalDetails(user.get());
    }
}
