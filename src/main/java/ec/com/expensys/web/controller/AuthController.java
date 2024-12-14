package ec.com.expensys.web.controller;

import ec.com.expensys.dto.AuthDto;
import ec.com.expensys.service.UserDetailServiceImpl;
import ec.com.expensys.web.exception.CustomResponse;
import ec.com.expensys.web.exception.MessageCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@RequestMapping("/v1/auth")
public class AuthController {

    private final UserDetailServiceImpl userDetailService;

    @PostMapping("/login")
    public ResponseEntity<CustomResponse> login(@Valid @RequestBody AuthDto personDto){
        String tokenJWT = userDetailService.getTokenToLoginUser(personDto);
        CustomResponse responseOk = CustomResponse.builder()
                .code(MessageCode.SUCCESS.getCode())
                .customMessage(tokenJWT)
                .path("/auth")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseOk);
    }
}
