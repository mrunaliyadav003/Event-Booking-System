package com.eventbookingapi.studentmeetup.controller;

import com.eventbookingapi.studentmeetup.collection.Student;
import com.eventbookingapi.studentmeetup.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin

@RequestMapping("students")

public class StudentController {
    @Autowired
    private StudentRepository studnetService;
    @PostMapping("create")
    public Student create(@RequestBody Student request) {
        return studnetService.save(request);
    }
@GetMapping("getAll")
    public List<Student> getAll(){
        return studnetService.findAll();
 }
@GetMapping("ByEmailId")
    public List<Student> getByEmailId(String emailId){
    return studnetService.findByName(emailId);

}
}
