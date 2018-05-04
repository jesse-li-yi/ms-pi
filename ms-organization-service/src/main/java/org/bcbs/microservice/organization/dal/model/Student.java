package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.constraint.DataView;
import org.bcbs.microservice.constraint.Gender;
import org.bcbs.microservice.dal.model.GenericEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student extends GenericEntity<Integer> {

    @NotBlank(message = "Invalid sn of student.")
    @Length(max = 16)
    @Column(length = 16, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String sn;

    @NotBlank(message = "Invalid name of student.")
    @Length(max = 16)
    @Column(nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @NotNull(message = "Invalid gender of student.")
    @Enumerated(value = EnumType.STRING)
    @Column(length = 10, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private Gender gender;

    @Length(max = 250)
    @Column(length = 250)
    @JsonView(value = {DataView.BasicView.class})
    private String avatar;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JsonView(value = {DataView.TypicalView.class})
    private Clazz clazz;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "students", cascade = {CascadeType.REFRESH})
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<Parent> parents;

    // Getter & setter.
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Clazz getClazz() {
        return clazz;
    }

    public void setClazz(Clazz clazz) {
        this.clazz = clazz;
    }

    public Set<Parent> getParents() {
        return parents;
    }

    public void setParents(Set<Parent> parents) {
        this.parents = parents;
    }
}
