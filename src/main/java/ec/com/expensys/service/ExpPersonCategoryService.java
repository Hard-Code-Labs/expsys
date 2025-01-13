package ec.com.expensys.service;

import ec.com.expensys.dto.CategoryInputDto;
import ec.com.expensys.dto.CategoryPageableDto;
import ec.com.expensys.dto.PersonCategoryDto;
import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.persistence.entity.ExpPersonCategory;
import ec.com.expensys.persistence.mappers.ExpPersonCategoryMapper;
import ec.com.expensys.persistence.repository.ExpPersonCategoryRepository;
import ec.com.expensys.persistence.repository.ExpPersonRepository;
import ec.com.expensys.web.utils.MessageCode;
import ec.com.expensys.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExpPersonCategoryService {

    private final ExpPersonCategoryRepository personCategoryRepository;
    private final ExpPersonCategoryMapper personCategoryMapper;
    private final ExpPersonRepository personRepository;

    private ExpPerson getPersonByUUID(String perUUID) {
        return personRepository.findByPerUUID(UUID.fromString(perUUID))
                .orElseThrow(() -> new NotFoundException(MessageCode.NOT_FOUND.getCode(),
                        "Person not found",
                        ExpPersonCategoryService.class.getName(),
                        false));
    }


    public CategoryPageableDto findCategoriesByPerson(String perUUID, int offset, int limit) {

        ExpPerson person = getPersonByUUID(perUUID);

        Pageable pageable = PageRequest.of(offset / limit,limit, Sort.by(Sort.Direction.ASC, "catName"));

        Page<ExpPersonCategory> categoriesPaginated = personCategoryRepository.findAllByExpPerson_PerId(person.getPerId(), pageable);

        List<PersonCategoryDto> categories = categoriesPaginated.getContent().stream()
                .map(personCategoryMapper::toExpPersonCategoryDto)
                .toList();

        return CategoryPageableDto.builder()
                .categories(categories)
                .totalPages(categoriesPaginated.getTotalPages())
                .hasNextPage(categoriesPaginated.hasNext())
                .build();
    }


    public PersonCategoryDto save(CategoryInputDto categoryInput) {

        ExpPerson person = getPersonByUUID(categoryInput.perUUID());

        ExpPersonCategory personCategory = personCategoryMapper.toExpPersonCategory(categoryInput);
        personCategory.setExpPerson(person);

        var personCategorySaved = personCategoryRepository.save(personCategory);
        return personCategoryMapper.toExpPersonCategoryDto(personCategorySaved);
    }
}
