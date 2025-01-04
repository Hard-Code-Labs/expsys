package ec.com.expensys.service;

import ec.com.expensys.dto.PersonCategoryDto;
import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.persistence.mappers.ExpPersonCategoryMapper;
import ec.com.expensys.persistence.repository.ExpPersonCategoryRepository;
import ec.com.expensys.persistence.repository.ExpPersonRepository;
import ec.com.expensys.web.utils.MessageCode;
import ec.com.expensys.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ExpPersonCategoryService {

    private final ExpPersonCategoryRepository personCategoryRepository;
    private final ExpPersonCategoryMapper personCategoryMapper;
    private final ExpPersonRepository personRepository;

    public List<PersonCategoryDto> findCategoriesByPerson(String perUUID) {

        ExpPerson person = personRepository.findByPerUUID(UUID.fromString(perUUID))
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND.getCode(),
                        "Person not found",
                        ExpPersonCategoryService.class.getName(),
                        false));

        return personCategoryMapper.toExpPersonCategoryDto(
                personCategoryRepository.findAllByExpPerson_PerId(person.getPerId())
        );
    }
}
