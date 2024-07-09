package com.example.studentadviceproject.service;

import com.example.studentadviceproject.dto.AdviceDto;
import com.example.studentadviceproject.dto.StudentDto;
import com.example.studentadviceproject.entity.Advice;
import com.example.studentadviceproject.entity.Student;
import com.example.studentadviceproject.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{


    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;

    public StudentServiceImpl(StudentRepository studentRepository, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Student createStudent(StudentDto studentDto) {
        @Valid Student student = modelMapper.map(studentDto, Student.class);
        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(StudentDto studentDto, AdviceDto adviceDto) {
        Advice advice = modelMapper.map(adviceDto, Advice.class);
        Student student = modelMapper.map(studentDto, Student.class);
        student.setAdvice(advice);
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public void deleteStudent(String kodeMelli) {
        studentRepository.deleteStudentByKodeMelli(kodeMelli);
    }

    @Override
    public List<StudentDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        List<StudentDto> studentDtos = new ArrayList<>();
        for(Student student : students) {
            StudentDto studentDto = modelMapper.map(student, StudentDto.class);
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }

    @Override
    public StudentDto getStudentByKodeMelli(String kodeMelli) {
        StudentDto studentDto = modelMapper.map(studentRepository.findStudentsByKodeMelli(kodeMelli), StudentDto.class);
        System.out.println(studentDto.getId());
        return studentDto;
    }

    @Override
    public List<StudentDto> getStudentsByAdviceId(Long adviceId) {
        List<Student> students = studentRepository.findStudentsByAdviceId(adviceId);
        List<StudentDto> studentDtos = new ArrayList<>();
        for(Student student : students) {
            StudentDto studentDto = modelMapper.map(student, StudentDto.class);
            studentDtos.add(studentDto);
        }
        return studentDtos;
    }


}
