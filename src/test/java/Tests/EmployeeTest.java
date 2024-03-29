package Tests;

import com.liquibase.entities.Employee;
import com.liquibase.entities.Project;
import com.liquibase.repositories.EmployeeDao;
import com.liquibase.repositories.ProjectDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Collectors;


public class EmployeeTest extends AbstractTest {

    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private EmployeeDao employeeDao;

//    @Autowired
//    private EmployeeProjectDao employeeProjectDao;

    @Test
    public void test() {

        Project project = new Project();
        project.setTitle("title");
        project = projectDao.save(project);
        logger.info(project.toString());
        Project project2 = new Project();
        project2.setTitle("title");
        project2 = projectDao.save(project2);
        Employee employee = new Employee();
        employee.setProjects(Arrays.asList(project, project2).stream().collect(Collectors.toSet()));
        employee = employeeDao.save(employee);
        Assert.assertEquals(2, employee.getProjects().size());
        employee.removeProject(project);
        employee = employeeDao.save(employee);
        Assert.assertEquals(1, employee.getProjects().size());

//        projectDao.delete(project2);

    }

}
