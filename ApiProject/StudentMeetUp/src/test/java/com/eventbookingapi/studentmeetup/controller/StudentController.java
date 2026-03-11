package com.eventbookingapi.studentmeetup.controller;
import com.eventbookingapi.studentmeetup.collection.Response;
import com.eventbookingapi.studentmeetup.collection.Student;
import com.eventbookingapi.studentmeetup.repository.StudentRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/students")
public class StudentController {
    @Autowired
        private StudentRepository studentRepository;

    @PostMapping
    public Response<Student> create(@RequestBody(required = false) Student reqItem) {
        reqItem.setName("ddsd");
        reqItem.setEmailId("ddsd");
        Response<Student> objData= new Response<Student>();
        objData.setData(studentRepository.save(reqItem));
        return objData;
    }

    @GetMapping
    public List<Student> getAll() {
        return studentRepository.findAll();
    }
}
