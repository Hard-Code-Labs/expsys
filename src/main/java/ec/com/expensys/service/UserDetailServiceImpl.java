package ec.com.expensys.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import ec.com.expensys.dto.TokenResponseDto;
import ec.com.expensys.dto.LoginRequestDto;
import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.persistence.repository.ExpPersonRepository;
import ec.com.expensys.security.JWTUtils;
import ec.com.expensys.web.utils.MessageCode;
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

    public TokenResponseDto getTokenToLoginUser(LoginRequestDto loginRequestDto) {
        Authentication authentication = authenticate(loginRequestDto);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.getNewAccessToken(authentication);
        String refreshToken = jwtUtils.getNewRefreshToken(authentication);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    public TokenResponseDto refreshToken(String tokenToRefresh) {
        DecodedJWT decodedJWT = jwtUtils.verifyToken(tokenToRefresh);
        String username = jwtUtils.getUsernameFromToken(decodedJWT);

        UserDetails userDetail = loadUserByUsername(username);
        Authentication userAuthenticated = new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword(), userDetail.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(userAuthenticated);
        String accessToken = jwtUtils.getNewAccessToken(userAuthenticated);
        String refreshToken = jwtUtils.getNewRefreshToken(userAuthenticated);

        return new TokenResponseDto(accessToken, refreshToken);
    }


    private Authentication authenticate(LoginRequestDto loginRequestDto) {
        UserDetails userDetail = loadUserByUsername(loginRequestDto.username());

        if (!passwordEncoder.matches(loginRequestDto.password(), userDetail.getPassword())) {
            throw new AuthenticationServiceException("Invalid username/password supplied");
        }

        return new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword(), userDetail.getAuthorities());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ExpPerson person = personRepository.findByPerMailAndIsEnabledTrueAndIsDeletedFalse(username)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND.getCode(),
                        "Person with mail " + username + " not found", UserDetailServiceImpl.class.getName(), false));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.addAll(loadRoles(person));
        authorities.addAll(loadPermissions(person));

        return new User(person.getPerMail(),
                person.getPerPassword(),
                person.getIsEnabled(),
                person.getIsEnabled(),
                person.getIsEnabled(),
                person.getIsEnabled(), authorities);
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
