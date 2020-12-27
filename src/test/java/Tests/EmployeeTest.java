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
        System.out.println(project.toString());
        Project project2 = new Project();
        project2.setTitle("title");
        project2 = projectDao.save(project2);
        Employee employee = new Employee();
        employee.setProjects(Arrays.asList(project, project2).stream().collect(Collectors.toSet()));
        employee = employeeDao.save(employee);

        project2.setEmployees(Arrays.asList(employee).stream().collect(Collectors.toSet()));
        project2 = projectDao.save(project2);
    }
}
