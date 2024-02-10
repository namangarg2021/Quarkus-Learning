package org.hprtech.entity;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.hprtech.repository.Student;

@ApplicationScoped
public class StudentRepository implements PanacheRepository<Student> {

}
