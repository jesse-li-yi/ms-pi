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
@Table(name = "institution")
public class Institution extends GenericEntity<Integer> {

    @NotBlank(message = "Invalid sn of institution.")
    @Length(max = 16)
    @Column(length = 16, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String sn;

    @NotBlank(message = "Invalid name of institution.")
    @Length(max = 32)
    @Column(length = 32, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "institution")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<President> presidents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "institution")
    @NotFound(action = NotFoundAction.IGNORE)
    private Set<School> schools;

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

    public Set<President> getPresidents() {
        return presidents;
    }

    public void setPresidents(Set<President> presidents) {
        this.presidents = presidents;
    }

    public Set<School> getSchools() {
        return schools;
    }

    public void setSchools(Set<School> schools) {
        this.schools = schools;
    }
}
