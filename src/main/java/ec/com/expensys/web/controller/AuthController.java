package ec.com.expensys.web.controller;

import ec.com.expensys.dto.TokenRequest;
import ec.com.expensys.dto.TokenResponseDto;
import ec.com.expensys.dto.LoginRequestDto;
import ec.com.expensys.service.UserDetailServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("denyAll()")
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserDetailServiceImpl userDetailService;


    @PostMapping("/login")
    @PreAuthorize("permitAll()")
    public ResponseEntity<TokenResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDetailService.getTokenToLoginUser(loginRequestDto));
    }

    @PostMapping("/refresh")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<TokenResponseDto> refresh(@Valid @RequestBody TokenRequest tokenToRefresh){
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDetailService.refreshToken(tokenToRefresh.token()));
    }
}
