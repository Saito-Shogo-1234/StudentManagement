package raisetech.Student.Management.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.Student.Management.data.Student;
import raisetech.Student.Management.service.StudentService;

@WebMvcTest(StudentController.class)

class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できてからのリストが返ってくること() throws  Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));


    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空で返ってくること() throws Exception {
    String id = "999";
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること()
      throws Exception {
    // リクエストデータは適切に構築して入力チェックの検証も兼ねている。
    // 本来であれば返り値は登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない。
    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                    "student": {
                        "id": "1",
                        "name": "山田 太郎",
                        "kanaName": "ヤマダ タロウ",
                        "nickname": "タロウ",
                        "email": "yamada@example.com",
                        "area": "東京都渋谷区",
                        "age": 25,
                        "sex": "男性",
                        "remark": null,
                        "deleted": false
                    },
                    "studentCourseList": [
                        {
                            "id": "1",
                            "studentId": "1",
                            "courseName": "プログラミング基礎",
                            "courseStartAt": "2025-04-01T10:00:00",
                            "courseEndAt": "2025-06-01T15:00:00"
                        }
                    ]
                }  
            """
    ))
    .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新が実行できて空で返ってくること() throws Exception {
    // リクエストデータは適切に構築して入力チェックの検証も兼ねている。
    mockMvc.perform(put("/updateStudent").contentType(MediaType.APPLICATION_JSON).content(
            """
                {
                    "student": {
                        "id": "1",
                        "name": "山田 太郎",
                        "kanaName": "ヤマダ タロウ",
                        "nickname": "タロウ",
                        "email": "yamada@example.com",
                        "area": "東京都渋谷区",
                        "age": 25,
                        "sex": "男性",
                        "remark": null,
                        "deleted": false
                    },
                    "studentCourseList": [
                        {
                            "id": "1",
                            "studentId": "1",
                            "courseName": "プログラミング基礎",
                            "courseStartAt": "2025-04-01T10:00:00",
                            "courseEndAt": "2025-06-01T15:00:00"
                        }
                    ]
                }  
            """
    ))
    .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の例外APIが実行できてステータスが400で返ってくること() throws Exception {
    mockMvc.perform(get("/exception"))
        .andExpect(status().is4xxClientError())
        .andExpect(content().string("このAPIは現在利用できません。古いURLとなっています。"));
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("7");
    student.setName("清宮幸太郎");
    student.setKanaName("キヨミヤコウタロウ");
    student.setNickname("キヨミ");
    student.setEmail("kiyomiya@example.com");
    student.setArea("東京");
    student.setSex("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた入力チェックに掛かること() {
    Student student = new Student();
    student.setId("テストです。");
    student.setName("清宮幸太郎");
    student.setKanaName("キヨミヤコウタロウ");
    student.setNickname("キヨミ");
    student.setEmail("kiyomiya@example.com");
    student.setArea("東京");
    student.setSex("男性");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください。");
  }
}