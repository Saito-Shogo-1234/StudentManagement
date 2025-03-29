package raisetech.StudentManagement.data;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import raisetech.StudentManagement.StudentsCourses;
import raisetech.StudentManagement.repository.StudentRepository;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping("/studentList")
	public List<Student> getStudentList() {
		return repository.search();
	}

	@GetMapping("/studentCourseList")
	public List<StudentsCourses> getStudentsCourseList() {
		return  repository.searchStudentsCourses();
	}
}

