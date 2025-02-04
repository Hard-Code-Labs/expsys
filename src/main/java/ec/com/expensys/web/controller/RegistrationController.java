package ec.com.expensys.web.controller;

import com.auth0.jwt.exceptions.TokenExpiredException;
import ec.com.expensys.security.JWTUtils;
import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.service.ExpPersonService;
import ec.com.expensys.dto.TokenRequest;
import ec.com.expensys.web.exception.*;
import ec.com.expensys.dto.RegisterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@PreAuthorize("permitAll()")
@RequestMapping("/v1/register")
@Tag(name = "Registration", description = "Endpoint for register new users.")
public class RegistrationController {

    private final ExpPersonService personService;
    private final JWTUtils jwtUtils;

    @Operation(
            summary = "Register new user",
            description = "Create new disabled person on database",
            tags = {"Registration"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Register new person",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = RegisterDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Custom Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class)
                            )
                    )
            }
    )
    @PostMapping("")
    public ResponseEntity<CustomResponse> register(@Valid @RequestBody RegisterDto personDto){
        personService.registerNewPerson(personDto);
        CustomResponse responseOk = CustomResponse.builder()
                .code(MessageCode.CREATED.getCode())
                .customMessage("New user registered successfully. Please check your email")
                .path("/register")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(responseOk);
    }


    @Operation(
            summary = "Validate user code",
            description = "Verification code from user's email",
            tags = {"Registration"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Validate person code",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = TokenRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Custom Error",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = CustomResponse.class)
                            )
                    )
            }
    )
    @PostMapping(path = "/confirmation")
    public ResponseEntity<?> confirmRegistration(@Valid @RequestBody TokenRequest tokenRequest) {

        ExpPerson person = personService.findByPerVerificationCode(tokenRequest.token())
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND.getCode(),
                        "Verification code not found. User has been removed from the database. Please register again",
                        RegistrationController.class.getName(),
                        false));

        if(person.getIsEnabled()){
            throw new DuplicateException(MessageCode.ALREADY_EXIST.getCode(),
                    "User already registered. Please redirect to login page",
                    RegistrationController.class.getName());
        }else{
            try {
                jwtUtils.verifyToken(person.getPerVerificationCode());
            } catch (TokenExpiredException e) {

                personService.deletePerson(person);
                throw new ExpiredTokenException("Token has expired. Please register again", JWTUtils.class.getName());
            }
        }

        personService.newPersonEnabled(person);

        CustomResponse responseOk = CustomResponse.builder()
                .code(MessageCode.SUCCESS.getCode())
                .customMessage("User registration successfully confirmed")
                .path("/register")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseOk);
    }
}
