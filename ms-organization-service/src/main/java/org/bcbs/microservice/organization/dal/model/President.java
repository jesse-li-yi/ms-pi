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
@Table(name = "president",
        uniqueConstraints = {@UniqueConstraint(name = "uk_phone_no", columnNames = "phoneNo")})
public class President extends GenericEntity<Integer> {

    @Pattern(regexp = RegexPattern.PHONE_NO, message = "Invalid phone number.")
    @Column(length = 11, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String phoneNo;

    @Embedded
    @JsonView(value = {DataView.BasicView.class})
    private PersonalInfo personalInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id",
            foreignKey = @ForeignKey(name = "fk_president_institution_id"))
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(value = {DataView.TypicalView.class})
    private Institution institution;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    @JoinTable(name = "president_school",
            joinColumns = {@JoinColumn(name = "president_id",
                    foreignKey = @ForeignKey(name = "fk_ps_president_id"))},
            inverseJoinColumns = {@JoinColumn(name = "school_id",
                    foreignKey = @ForeignKey(name = "fk_ps_school_id"))},
            indexes = {@Index(name = "fk_ps_president_id", columnList = "president_id"),
                    @Index(name = "fk_ps_school_id", columnList = "school_id")})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<School> schools;

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

    public Institution getInstitution() {
        return institution;
    }

    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public Set<School> getSchools() {
        return schools;
    }

    public void setSchools(Set<School> schools) {
        this.schools = schools;
    }
}
