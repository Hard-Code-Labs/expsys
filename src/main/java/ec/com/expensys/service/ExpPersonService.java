package ec.com.expensys.service;

import ec.com.expensys.config.security.JWTUtils;
import ec.com.expensys.persistence.entity.ExpCountry;
import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.persistence.entity.ExpRole;
import ec.com.expensys.persistence.entity.ExpRolePerson;
import ec.com.expensys.persistence.mappers.ExpPersonMapper;
import ec.com.expensys.persistence.repository.ExpCountryRepository;
import ec.com.expensys.persistence.repository.ExpPersonRepository;
import ec.com.expensys.persistence.repository.ExpRolePersonRepository;
import ec.com.expensys.persistence.repository.ExpRoleRepository;
import ec.com.expensys.service.dto.ExpPersonDto;
import ec.com.expensys.service.dto.RegistrationDto;
import ec.com.expensys.web.exception.DuplicateException;
import ec.com.expensys.web.exception.ErrorCode;
import ec.com.expensys.web.exception.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class ExpPersonService {

    private final ExpPersonMapper expPersonMapper;

    private final ExpRolePersonRepository expRolePersonRepository;
    private final ExpCountryRepository expCountryRepository;
    private final ExpPersonRepository expPersonRepository;
    private final ExpRoleRepository expRoleRepository;
    private final PasswordEncoder passwordEncoder;

    private final CryptoService cryptoService;
    private final EmailService emailService;
    private final JWTUtils jwtUtils;


    @Autowired
    public ExpPersonService(JWTUtils jwtUtils, PasswordEncoder passwordEncoder, ExpPersonMapper expPersonMapper,
                            ExpRolePersonRepository expRolePersonRepository,
                            ExpPersonRepository expPersonRepository,
                            ExpCountryRepository expCountryRepository,
                            ExpRoleRepository expRoleRepository,
                            CryptoService cryptoService,
                            EmailService emailService) {

        this.passwordEncoder = passwordEncoder;
        this.expRolePersonRepository = expRolePersonRepository;
        this.expCountryRepository = expCountryRepository;
        this.expPersonRepository = expPersonRepository;
        this.expRoleRepository = expRoleRepository;
        this.expPersonMapper = expPersonMapper;
        this.cryptoService = cryptoService;
        this.emailService = emailService;
        this.jwtUtils = jwtUtils;
    }

    public List<ExpPersonDto> findAll(){
        List<ExpPerson> result = expPersonRepository.findAll();
        return expPersonMapper.toPersonsDto(result);
    }


    @Transactional
    public ExpPersonDto registerNewPerson(RegistrationDto person) {

        if(emailExists(person.getPerMail())){
            throw new DuplicateException(ErrorCode.ALREADY_EXIST.getCode(),
                    "Email already exists.",
                    ExpPerson.class.getName());
        }

        String decryptPassword = decryptPassword(person);
        String tokenOnRegister = jwtUtils.createOnRegister(person.getPerMail());

        ExpCountry personCountry = expCountryRepository.findById(person.getCountryId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND.getCode(),
                                "Country not found by id",
                                ExpPersonService.class.getName(),
                                false));

        ExpPerson personSaved = saveNewPerson(person,decryptPassword,personCountry,tokenOnRegister);

        saveNewRolePerson(2L,personSaved);//TODO hacer enum de los roles
        sendMailToVerifyAccount(personSaved);

        return expPersonMapper.toPersonDto(personSaved);
    }

    private boolean emailExists(final String email) {
        return expPersonRepository.findByPerMail(email) != null;
    }

    private String decryptPassword(RegistrationDto person){
        return cryptoService.decrypt(person.getPerPassword());
    }

    private ExpPerson saveNewPerson(RegistrationDto person, String password, ExpCountry country, String tokenOnRegister){
        ExpPerson personToSave = new ExpPerson();
        personToSave.setPerUUID(UUID.randomUUID());
        personToSave.setPerMail(person.getPerMail());
        personToSave.setPerName(person.getPerName());
        personToSave.setPerLastname(person.getPerLastname());
        personToSave.setPerPassword(passwordEncoder.encode(password));
        personToSave.setIsEnabled(false);
        personToSave.setExpCountry(country);
        personToSave.setPerVerificationCode(tokenOnRegister);

        return expPersonRepository.save(personToSave);
    }

    private void saveNewRolePerson(Long roleId, ExpPerson personSaved){
        ExpRole basicRole = expRoleRepository.findById(roleId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND.getCode(),
                        "Role not found by id.",
                        ExpPersonService.class.getName(),
                        false));

        ExpRolePerson rolePerson = new ExpRolePerson();
        rolePerson.setExpPerson(personSaved);
        rolePerson.setExpRole(basicRole);
        rolePerson.setRopActive(true);
        rolePerson.setRopStartDate(LocalDate.now());

        expRolePersonRepository.save(rolePerson);
    }


    //TODO parametrizar URL
    private void sendMailToVerifyAccount(ExpPerson person) {
        String URL_REDIRECT_POST_REGISTER = "http://localhost:3000/login/email-confirm?token=";
        String URL_TO_CONFIRM = URL_REDIRECT_POST_REGISTER + person.getPerVerificationCode();

        Map<String, Object> data = getMapToMail(person, URL_TO_CONFIRM);

        emailService.sendEmail(person.getPerMail(),
                "Welcome to Moneyatic",
                data,
                "confirmRegisterMail.ftl");
    }

    private Map<String, Object> getMapToMail(ExpPerson person, String URI){
        Map<String, Object> model = new HashMap<>();
        if(person != null){
            model.put("perName", person.getPerName());
            model.put("URI", URI);
        }

        return model;
    }

    public Optional<ExpPerson> findByPerVerificationCode(String verificationCode){
        return expPersonRepository.findByPerVerificationCode(verificationCode);
    }

    public void newPersonEnabled(ExpPerson personUpdated){
        personUpdated.setIsEnabled(true);
        expPersonRepository.save(personUpdated);
    }

    public void deletePerson(ExpPerson personToDelete){
        expPersonRepository.delete(personToDelete);
    }
}

