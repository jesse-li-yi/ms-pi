package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.common.constraint.DataView;
import org.bcbs.microservice.common.constraint.RegexPattern;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.bcbs.microservice.organization.dal.model.embeddable.PersonalInfo;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Table(name = "parent",
        uniqueConstraints = {@UniqueConstraint(name = "uk_phone_no", columnNames = "phoneNo")})
public class Parent extends GenericEntity<Integer> {

    @Pattern(regexp = RegexPattern.PHONE_NO, message = "Invalid phone number.")
    @Column(length = 11, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String phoneNo;

    @Embedded
    @JsonView(value = {DataView.BasicView.class})
    private PersonalInfo personalInfo;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "parent_student",
            joinColumns = {@JoinColumn(name = "parent_id",
                    foreignKey = @ForeignKey(name = "fk_ps_parent_id"))},
            inverseJoinColumns = {@JoinColumn(name = "student_id",
                    foreignKey = @ForeignKey(name = "fk_ps_student_id"))},
            indexes = {@Index(name = "fk_ps_parent_id", columnList = "parent_id"),
                    @Index(name = "fk_ps_student_id", columnList = "student_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Student> students;

    // Getter & setter.
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
