package Tests;

import com.liquibase.repositories.AbstractEntityDao;
import com.liquibase.repositories.NoteDao;
import com.liquibase.services.AServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
class DbMockTest {
    @Mock
    private NoteDao noteDao;

    @Mock
    private AbstractEntityDao abstractEntityDao;
    @InjectMocks
    private AServiceImpl aService;

    @BeforeAll
    static void init() {
        initMocks(DbMockTest.class);
    }

    @Test
    void testGetPrincipleWithRole() {
        aService.start();
    }
}
