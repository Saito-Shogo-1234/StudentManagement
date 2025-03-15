package raisetech.StudentManagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

	@Autowired
	private StudentRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagementApplication.class, args);
	}

	@GetMapping("/student")
	public String getStudent(@RequestParam("name") String name) {
		Student student = repository.searchByName(name);
		return student.getName() + " " + student.getAge();
	}

	@PostMapping("/student")
	public void registerStudent(String name, int age) {
		repository.registerStudent(name, age);

	}

	@PatchMapping("/student")
	public  void updateStudent(String  name, int age) {
		repository.updateStudent(name,age);
	}

	@DeleteMapping("/student")
	public  void deleteStudent(String name){
		repository.deleteStudent(name);
	}

	//GETとPOST
	//Getは取得
	//POSTは渡す

}

//授業が終わったらプッシュ