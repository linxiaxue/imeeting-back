package fudan.se.lab2.service;

import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author LBW
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Implement the function.
        Optional<User> users = Optional.ofNullable(userRepository.findByUsername(username));
        if(!users.isPresent()) {
            throw new UsernameNotFoundException("User: '" + username + "' not found.");
        }
        Set<Authority> authorities = (Set<Authority>) userRepository.findByUsername(username).getAuthorities();
        //authList.add(new SimpleAu);

        UserDetails userDetail = new User(users.get().getUsername(), users.get().getPassword(),users.get().getFullname(),authorities);
        return userDetail;

    }
}
