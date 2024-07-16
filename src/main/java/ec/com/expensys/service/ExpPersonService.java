package ec.com.expensys.service;

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

    private final PasswordEncoder passwordEncoder;

    private final ExpPersonMapper expPersonMapper;
    private final ExpRolePersonRepository expRolePersonRepository;
    private final ExpCountryRepository expCountryRepository;
    private final ExpPersonRepository expPersonRepository;
    private final ExpRoleRepository expRoleRepository;
    private final MessageSource messageSource;
    private final CryptoService cryptoService;


    @Autowired
    public ExpPersonService(PasswordEncoder passwordEncoder,
                            ExpPersonMapper expPersonMapper,
                            ExpRolePersonRepository expRolePersonRepository,
                            ExpPersonRepository expPersonRepository,
                            ExpCountryRepository expCountryRepository,
                            ExpRoleRepository expRoleRepository,
                            MessageSource messageSource,
                            CryptoService cryptoService) {

        this.passwordEncoder = passwordEncoder;
        this.expRolePersonRepository = expRolePersonRepository;
        this.expCountryRepository = expCountryRepository;
        this.expPersonRepository = expPersonRepository;
        this.expRoleRepository = expRoleRepository;
        this.expPersonMapper = expPersonMapper;
        this.messageSource = messageSource;
        this.cryptoService = cryptoService;
    }

    public List<ExpPersonDto> findAll(){
        List<ExpPerson> result = expPersonRepository.findAll();
        return expPersonMapper.toPersonsDto(result);
    }


    @Transactional
    public ExpPersonDto registerNewPerson(ExpPersonDto personDto){
        if(personDto.getPerMail().isEmpty()){
            throw new RuntimeException("El mail no puede estar vacio");
        }

        if(emailExists(personDto.getPerMail())){
            throw new MailAlreadyExistException();
        }

        String decryptPassword = "";
        try {
            decryptPassword = cryptoService.decrypt(personDto.getPerPassword());
        }catch (Exception e){
            log.error("Error al decifrar el password. {}",e.getMessage());
            throw new RuntimeException("Error al decifrar el password con la llave privada");
        }

        ExpCountry personCountry = expCountryRepository.findById(personDto.getCountryId())
                .orElseThrow();

        ExpPerson personToSave = new ExpPerson();
        personToSave.setPerUUID(UUID.randomUUID());
        personToSave.setPerMail(personDto.getPerMail());
        personToSave.setPerName(personDto.getPerName());
        personToSave.setPerLastname(personDto.getPerLastname());
        personToSave.setPerPassword(passwordEncoder.encode(decryptPassword));
        personToSave.setIsEnabled(false);
        personToSave.setExpCountry(personCountry);

        ExpPerson personSaved = expPersonRepository.save(personToSave);

        ExpRole basicRole = expRoleRepository.findById(2L).orElseThrow();

        ExpRolePerson rolePerson = new ExpRolePerson();
        rolePerson.setExpPerson(personSaved);
        rolePerson.setExpRole(basicRole);
        rolePerson.setRopActive(true);
        rolePerson.setRopStartDate(LocalDate.now());

        expRolePersonRepository.save(rolePerson);

        return expPersonMapper.toPersonDto(personSaved);
    }

    private boolean emailExists(final String email) {
        return expPersonRepository.findByPerMail(email) != null;
    }
}
