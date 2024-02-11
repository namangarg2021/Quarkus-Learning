package org.hprtech.entity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.hprtech.repository.Student;

import java.util.List;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {
    public List<Student> getStudentByBranch(String branch) {
        return list("select s from Student s where s.branch=?1", branch);
    }
}
