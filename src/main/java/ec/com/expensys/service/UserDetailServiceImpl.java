package ec.com.expensys.service;

import ec.com.expensys.dto.AuthDto;
import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.persistence.repository.ExpPersonRepository;
import ec.com.expensys.security.JWTUtils;
import ec.com.expensys.web.exception.MessageCode;
import ec.com.expensys.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final JWTUtils jwtUtils;
    private final ExpPersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public String getTokenToLoginUser(AuthDto personDto) {
        Authentication authentication = authenticate(personDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtUtils.getNewToken(authentication);
    }

    private Authentication authenticate(AuthDto personDto) {
        UserDetails userDetail = loadUserByUsername(personDto.username());

        if (!passwordEncoder.matches(personDto.password(), userDetail.getPassword())) {
            throw new AuthenticationServiceException("Invalid username/password supplied");
        }


        return new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword(), userDetail.getAuthorities());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ExpPerson person = personRepository.findActiveByMail(username)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND.getCode(),
                        "Person with mail " + username + " not found", UserDetailServiceImpl.class.getName(), false));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(loadRoles(person));
        authorities.addAll(loadPermissions(person));

        return new User(person.getPerMail(),
                person.getPerPassword(), true,
                true,
                true,
                true, authorities);
    }

    private List<SimpleGrantedAuthority> loadRoles(ExpPerson person) {
        return person.getRoleList().stream()
                .map(rolePerson -> {
                    String roleName = "ROLE_" + rolePerson.getExpRole().getRolName().name();
                    return new SimpleGrantedAuthority(roleName);
                })
                .toList();
    }

    private List<SimpleGrantedAuthority> loadPermissions(ExpPerson person) {
        return person.getRoleList().stream()
                .flatMap(rolePerson -> rolePerson.getExpRole().getPermissions().stream())
                .map(permission -> {
                    String permissionName = permission.getPermission().getPrmName();
                    return new SimpleGrantedAuthority(permissionName);
                })
                .toList();
    }
}
