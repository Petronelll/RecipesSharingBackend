package recipes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    UserRepository userRepository;

    @Autowired
    UserDetailsServiceImpl(UserRepository userService) {
        this.userRepository = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) throw new EmailNotFoundException("Not found: " + email);
        return new UserDetailsImpl(user);
    }

    static class EmailNotFoundException extends AuthenticationException {

        public EmailNotFoundException(String msg) {
            super(msg);
        }
    }
}