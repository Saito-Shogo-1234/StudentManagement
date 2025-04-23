package raisetech.Student.Management.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.Student.Management.data.StudentsCourses;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.domain.StudentDetail;
import raisetech.Student.Management.repository.StudentRepository;

/**
 *　受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新処理を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  /**
   * 受講生一覧検索です
   * 全権検索を行うので、条件指定を行いません。
   *
   * @return　受講生一覧(全件)
   */
  public List<Student> searchStudentList() {
    return repository.search();
  }

  /**
   * 受講生検索です。
   * IDに紐づく受講生情報を取得した後、その受講生に紐づく受講生コース情報を取得して設定します。
   *
   * @param id　受講生ID
   * @return　受講生
   */
  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    return  repository.searchStudentsCoursesList();
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail){
    repository.registerStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourses : studentDetail.getStudentsCourses()) {
      studentsCourses.setStudentid(studentDetail.getStudent().getId());
      studentsCourses.setCourseStartAt(LocalDateTime.now());
      studentsCourses.setCourseEndAt(LocalDateTime.now().plusYears(1));
      repository.registerStudentsCourses(studentsCourses);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourse : studentDetail.getStudentsCourses()) {
      studentsCourse.setStudentid(studentDetail.getStudent().getId());
      repository.updateStudentsCourses(studentsCourse);
    }
  }
}
