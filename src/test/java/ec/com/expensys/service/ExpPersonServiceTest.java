package ec.com.expensys.service;

import ec.com.expensys.persistence.entity.ExpPerson;
import ec.com.expensys.persistence.repository.ExpPersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpPersonServiceTest {

    @Mock
    ExpPersonRepository personRepository;


    @Test
    public void emailExistsTest() {

        when(personRepository.findByPerMail(anyString()))
                .thenReturn(new ExpPerson());

        ExpPerson result = personRepository.findByPerMail(anyString());

        assertNotNull(result);
    }
}
