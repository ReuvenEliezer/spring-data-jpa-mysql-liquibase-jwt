package Tests;

import com.liquibase.LiquibaseApplication;
import com.liquibase.entities.Employee;
import com.liquibase.entities.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.liquibase.repositories.EmployeeDao;
import com.liquibase.repositories.ProjectDao;

import java.util.Arrays;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = LiquibaseApplication.class)
public class EmployeeTest {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EmployeeDao employeeDao;


    @Test
    public void test() {

        Project project = new Project();
        project.setTitle("title");
        project = projectDao.save(project);
        Employee employee = new Employee();
        employee.setProjects(Arrays.asList(project).stream().collect(Collectors.toSet()));
        employee = employeeDao.save(employee);

    }
}
