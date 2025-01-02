package ec.com.expensys.service;

import ec.com.expensys.dto.PersonDataDto;
import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.persistence.mappers.ExpPersonMapper;
import ec.com.expensys.persistence.repository.ExpPersonRepository;
import ec.com.expensys.web.exception.MessageCode;
import ec.com.expensys.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class PersonDataService {

    private final ExpPersonRepository personRepository;
    private final ExpPersonMapper personMapper;

    public PersonDataDto getAllDataByPerMail(String perMail) {
        ExpPerson person = personRepository.findActiveByMail(perMail)
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND.getCode(),
                        "Person by mail " + perMail + " not found or is deleted",
                        ExpPersonCategoryService.class.getName(),
                        false));

        return personMapper.toPersonDataDto(person);
    }
}
