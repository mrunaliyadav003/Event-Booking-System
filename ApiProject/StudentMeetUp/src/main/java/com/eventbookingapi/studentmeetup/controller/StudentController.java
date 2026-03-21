package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.collection.Response;
import com.eventbookingapi.studentmeetup.collection.Student;
import com.eventbookingapi.studentmeetup.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin

@RequestMapping("students")

public class StudentController {
    @Autowired
    private StudentRepository studentRepository;
    @PostMapping("create")
    public Student create(@RequestBody Student request) {
        if (request != null) {
            request.setSubscribed(true);
        }
        return studentRepository.save(request);
    }
@GetMapping("getAll")
    public List<Student> getAll(){
        return studentRepository.findAll();
 }
@GetMapping("ByEmailId")
    public List<Student> getByEmailId(@RequestParam String emailId){
    return studentRepository.findByEmailId(emailId);

}

    @PostMapping("subscribe")
    public ResponseEntity<Response> subscribe(@RequestBody Student request) {
        if (request == null || isBlank(request.getEmailId())) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .Success(false)
                    .Message("EmailId is required")
                    .build());
        }

        Student student = studentRepository.findByEmailId(request.getEmailId())
                .stream()
                .findFirst()
                .orElseGet(Student::new);

        student.setEmailId(request.getEmailId());
        if (!isBlank(request.getName())) {
            student.setName(request.getName());
        }
        student.setSubscribed(true);

        Student saved = studentRepository.save(student);
        return ResponseEntity.ok(Response.builder()
                .Success(true)
                .Message("Subscribed successfully")
                .Data(saved)
                .build());
    }

    @PostMapping("unsubscribe")
    public ResponseEntity<Response> unsubscribe(@RequestBody Map<String, String> request) {
        String emailId = request == null ? null : request.get("emailId");
        if (isBlank(emailId)) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .Success(false)
                    .Message("EmailId is required")
                    .build());
        }

        Student student = studentRepository.findByEmailId(emailId)
                .stream()
                .findFirst()
                .orElse(null);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Response.builder()
                    .Success(false)
                    .Message("Student not found")
                    .build());
        }

        student.setSubscribed(false);
        Student saved = studentRepository.save(student);

        return ResponseEntity.ok(Response.builder()
                .Success(true)
                .Message("Unsubscribed successfully")
                .Data(saved)
                .build());
    }

    @GetMapping("subscription")
    public ResponseEntity<Response> subscription(@RequestParam String emailId) {
        if (isBlank(emailId)) {
            return ResponseEntity.badRequest().body(Response.builder()
                    .Success(false)
                    .Message("EmailId is required")
                    .build());
        }

        Student student = studentRepository.findByEmailId(emailId)
                .stream()
                .findFirst()
                .orElse(null);

        boolean subscribed = student != null && Boolean.TRUE.equals(student.getSubscribed());
        return ResponseEntity.ok(Response.builder()
                .Success(true)
                .Data(Map.of("subscribed", subscribed))
                .build());
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
