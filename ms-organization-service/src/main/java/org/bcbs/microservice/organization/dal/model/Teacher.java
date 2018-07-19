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
@Table(name = "teacher",
        uniqueConstraints = {@UniqueConstraint(name = "uk_phone_no", columnNames = "phoneNo")})
public class Teacher extends GenericEntity<Integer> {

    @Pattern(regexp = RegexPattern.PHONE_NO, message = "Invalid phone number.")
    @Column(length = 11, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String phoneNo;

    @Embedded
    @JsonView(value = {DataView.BasicView.class})
    private PersonalInfo personalInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id",
            foreignKey = @ForeignKey(name = "fk_teacher_school_id"))
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(value = {DataView.TypicalView.class})
    private School school;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "teacher_clazz",
            joinColumns = {@JoinColumn(name = "teacher_id",
                    foreignKey = @ForeignKey(name = "fk_tc_teacher_id"))},
            inverseJoinColumns = {@JoinColumn(name = "clazz_id",
                    foreignKey = @ForeignKey(name = "fk_tc_clazz_id"))},
            indexes = {@Index(name = "fk_tc_teacher_id", columnList = "teacher_id"),
                    @Index(name = "fk_tc_clazz_id", columnList = "clazz_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Clazz> clazzes;

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

    public School getSchool() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Set<Clazz> getClazzes() {
        return clazzes;
    }

    public void setClazzes(Set<Clazz> clazzes) {
        this.clazzes = clazzes;
    }
}
