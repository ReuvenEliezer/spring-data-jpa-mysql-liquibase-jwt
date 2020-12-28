package Tests;

import com.liquibase.entities.Course;
import com.liquibase.entities.CourseRating;
import com.liquibase.entities.Student;
import com.liquibase.repositories.CourseDao;
import com.liquibase.repositories.CourseRatingDao;
import com.liquibase.repositories.StudentDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class CourseStudentTest extends AbstractTest {


    @Autowired
    private CourseDao courseDao;

    @Autowired
    private StudentDao studentDao;

    @Autowired
    private CourseRatingDao courseRatingDao;


    @Test
    public void test() {
        Student student1 = new Student();
        student1.setName("student 1");
        student1 = studentDao.save(student1);
        System.out.println(student1.toString());

        Student student2 = new Student();
        student2.setName("student 2");
        student2 = studentDao.save(student2);
        System.out.println(student2.toString());

        Course mathCourse = new Course();
        mathCourse.setName("Math");
        mathCourse = courseDao.save(mathCourse);
        System.out.println(mathCourse.toString());

        Course physicalCourse = new Course();
        physicalCourse.setName("Physical");
        physicalCourse = courseDao.save(physicalCourse);
        System.out.println(physicalCourse.toString());

        CourseRating courseRating = new CourseRating(student1, mathCourse, 90d);
        CourseRating save = courseRatingDao.save(courseRating);
        Student studentRatings = studentDao.findById(save.getStudent().getId()).get();
        System.out.println(studentRatings.getRatings().toString());
        Assert.assertEquals(2, courseDao.findAll().size());
        Assert.assertEquals(1, studentRatings.getRatings().size());
    }

}
