package ec.com.expensys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ExpPersonSecurityService implements UserDetailsService {


    private final ExpPersonService expPersonService;

    @Autowired
    public ExpPersonSecurityService(ExpPersonService expPersonService) {
        this.expPersonService = expPersonService;
    }




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
