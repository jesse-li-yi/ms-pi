package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.constraint.DataView;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "clazz")
public class Clazz extends GenericEntity<Integer> {

    @NotBlank(message = "Invalid name of class.")
    @Length(max = 32)
    @Column(length = 32, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(value = {DataView.TypicalView.class})
    private School school;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clazz")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Student> students;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "clazzes", cascade = {CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Teacher> teachers;

    // Getter & setter.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }
}
