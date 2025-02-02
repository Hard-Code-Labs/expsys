package ec.com.expensys.service;

import ec.com.expensys.security.JWTUtils;
import ec.com.expensys.persistence.entity.ExpCountry;
import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.persistence.entity.ExpRole;
import ec.com.expensys.persistence.entity.ExpRolePerson;
import ec.com.expensys.persistence.mappers.ExpPersonMapper;
import ec.com.expensys.persistence.repository.ExpCountryRepository;
import ec.com.expensys.persistence.repository.ExpPersonRepository;
import ec.com.expensys.persistence.repository.ExpRolePersonRepository;
import ec.com.expensys.persistence.repository.ExpRoleRepository;
import ec.com.expensys.web.exception.DuplicateException;
import ec.com.expensys.web.exception.MessageCode;
import ec.com.expensys.web.exception.NotFoundException;
import ec.com.expensys.dto.PersonDto;
import ec.com.expensys.dto.RegisterDto;
import ec.com.expensys.web.utils.RoleEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpPersonService {

    private final ExpPersonMapper expPersonMapper;

    private final ExpRolePersonRepository expRolePersonRepository;
    private final ExpCountryRepository expCountryRepository;
    private final ExpPersonRepository personRepository;
    private final ExpRoleRepository expRoleRepository;
    private final PasswordEncoder passwordEncoder;

    private final CryptoService cryptoService;
    private final EmailService emailService;
    private final JWTUtils jwtUtils;

    @Value("${constant.url_redirect_after_register}")
    private String URL_REDIRECT_AFTER_REGISTER;


    public List<PersonDto> findAll() {
        List<ExpPerson> result = personRepository.findAll();
        return expPersonMapper.toPersonsDto(result);
    }


    @Transactional
    public void registerNewPerson(RegisterDto registerDto) {

        ExpPerson person = personRepository.findByPerMail(registerDto.perMail().toLowerCase())
                .orElse(null);

        if (person != null) {
            throw new DuplicateException(MessageCode.ALREADY_EXIST.getCode(),
                    "Email already exists.",
                    ExpPerson.class.getName());
        }

        String decryptPassword = cryptoService.decrypt(registerDto.perPassword());
        String tokenOnRegister = jwtUtils.getNewTokenOnRegister(registerDto.perMail().toLowerCase());

        ExpCountry personCountry = expCountryRepository.findById(registerDto.countryId())
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND.getCode(),
                        "Country not found by id",
                        ExpPersonService.class.getName(),
                        false));

        ExpPerson personSaved = saveNewPerson(registerDto, decryptPassword, personCountry, tokenOnRegister);

        saveNewRolePerson(RoleEnum.BASIC, personSaved);

        sendMailToVerifyAccount(personSaved);
    }

    private ExpPerson saveNewPerson(RegisterDto person, String password, ExpCountry country, String tokenOnRegister) {
        ExpPerson personToSave = new ExpPerson();
        personToSave.setPerUUID(UUID.randomUUID());
        personToSave.setPerMail(person.perMail().toLowerCase());
        personToSave.setPerName(person.perName());
        personToSave.setPerLastname(person.perLastname());
        personToSave.setPerPassword(passwordEncoder.encode(password));
        personToSave.setIsEnabled(false);
        personToSave.setExpCountry(country);
        personToSave.setPerVerificationCode(tokenOnRegister);

        return personRepository.save(personToSave);
    }

    private void saveNewRolePerson(RoleEnum role, ExpPerson personSaved) {
        ExpRole rol = expRoleRepository.findByRolName(role)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND.getCode(),
                        "Role " + role + " not found",
                        ExpPersonService.class.getName(),
                        false));

        ExpRolePerson rolePerson = new ExpRolePerson();
        rolePerson.setExpPerson(personSaved);
        rolePerson.setExpRole(rol);
        rolePerson.setRopActive(true);
        rolePerson.setRopStartDate(LocalDate.now());

        expRolePersonRepository.save(rolePerson);
    }


    private void sendMailToVerifyAccount(ExpPerson person) {
        String URL_TO_CONFIRM = URL_REDIRECT_AFTER_REGISTER + person.getPerVerificationCode();

        Map<String, Object> data = getMapToMail(person, URL_TO_CONFIRM);

        emailService.sendEmail(person.getPerMail(),
                "Welcome to Moneyatic",
                data,
                "confirmRegisterMail.ftl");
    }

    private Map<String, Object> getMapToMail(ExpPerson person, String URI) {
        Map<String, Object> model = new HashMap<>();
        if (person != null) {
            model.put("perName", person.getPerName());
            model.put("URI", URI);
        }

        return model;
    }

    public Optional<ExpPerson> findByPerVerificationCode(String verificationCode) {
        return personRepository.findByPerVerificationCode(verificationCode);
    }

    public void newPersonEnabled(ExpPerson personUpdated) {
        personUpdated.setIsEnabled(true);
        personRepository.save(personUpdated);
    }

    public void deletePerson(ExpPerson personToDelete) {
        personRepository.delete(personToDelete);
    }
}

