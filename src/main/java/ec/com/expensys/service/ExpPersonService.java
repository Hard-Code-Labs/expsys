package ec.com.expensys.service;

import com.resend.core.exception.ResendException;
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
import ec.com.expensys.web.exception.MailAlreadyExistException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class ExpPersonService {

    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ExpPersonMapper expPersonMapper;
    private final ExpRolePersonRepository expRolePersonRepository;
    private final ExpCountryRepository expCountryRepository;
    private final ExpPersonRepository expPersonRepository;
    private final ExpRoleRepository expRoleRepository;
    private final MessageSource messageSource;
    private final CryptoService cryptoService;
    private final EmailService emailService;


    @Autowired
    public ExpPersonService(ExpPersonMapper expPersonMapper,
                            ExpRolePersonRepository expRolePersonRepository,
                            ExpPersonRepository expPersonRepository,
                            ExpCountryRepository expCountryRepository,
                            ExpRoleRepository expRoleRepository,
                            MessageSource messageSource,
                            CryptoService cryptoService,
                            EmailService emailService) {

        this.expRolePersonRepository = expRolePersonRepository;
        this.expCountryRepository = expCountryRepository;
        this.expPersonRepository = expPersonRepository;
        this.expRoleRepository = expRoleRepository;
        this.expPersonMapper = expPersonMapper;
        this.messageSource = messageSource;
        this.cryptoService = cryptoService;
        this.emailService = emailService;
    }

    public List<ExpPersonDto> findAll(){
        List<ExpPerson> result = expPersonRepository.findAll();
        return expPersonMapper.toPersonsDto(result);
    }


    @Transactional
    public ExpPersonDto registerNewPerson(RegistrationDto person) throws ResendException {

        if(person.getPerMail().isEmpty()){
            throw new RuntimeException("El mail no puede estar vacio");
        }

        if(emailExists(person.getPerMail())){
            throw new MailAlreadyExistException();
        }

        String decryptPassword = decryptPassword(person);
        String tokenOnRegister = jwtUtils.createOnRegister(person.getPerName());
        ExpCountry personCountry = expCountryRepository.findById(person.getCountryId()).orElseThrow();

        ExpPerson personSaved = saveNewPerson(person,decryptPassword,personCountry,tokenOnRegister);

        saveNewRolePerson(2L,personSaved);
        sendMailToVerifyAccount(personSaved);

        return expPersonMapper.toPersonDto(personSaved);
    }

    private boolean emailExists(final String email) {
        return expPersonRepository.findByPerMail(email) != null;
    }

    private String decryptPassword(RegistrationDto person){
        if(person.getPerPassword().isEmpty()){
            throw new RuntimeException("El password no puede estar vacio");
        }

        try {
            return cryptoService.decrypt(person.getPerPassword());
        }catch (Exception e){
            log.error("Error al decifrar el password. {}",e.getMessage());
            throw new RuntimeException("Error al decifrar el password con la llave privada");
        }
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
        ExpRole basicRole = expRoleRepository.findById(roleId).orElseThrow();

        ExpRolePerson rolePerson = new ExpRolePerson();
        rolePerson.setExpPerson(personSaved);
        rolePerson.setExpRole(basicRole);
        rolePerson.setRopActive(true);
        rolePerson.setRopStartDate(LocalDate.now());

        expRolePersonRepository.save(rolePerson);
    }

    private void sendMailToVerifyAccount(ExpPerson person) throws ResendException {
        String URL_REDIRECT_POST_REGISTER = "http://localhost:3000/login/email-confirm?token=";
        emailService.sendEmail(person.getPerMail(),
                "Welcome to Moneyatic",
                URL_REDIRECT_POST_REGISTER + person.getPerVerificationCode());
    }

    public ExpPerson findByPerVerificationCode(String verificationCode){
        ExpPerson person = expPersonRepository.findByPerVerificationCode(verificationCode)
                .orElseThrow(() -> new RuntimeException("El codigo de verificacion ya no existe"));

        if(person.getIsEnabled()){
            throw new RuntimeException("Email ya fue verificado");
        }

        return person;
    }

    public boolean verifyToken(String token){
        return jwtUtils.isValid(token);
    }

    public void enablePerson(ExpPerson personUpdated){
        personUpdated.setIsEnabled(true);
        personUpdated.setPerVerificationCode(null);
        expPersonRepository.save(personUpdated);
    }
}

