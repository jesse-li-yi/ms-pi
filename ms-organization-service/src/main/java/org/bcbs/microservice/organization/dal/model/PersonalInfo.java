package org.bcbs.microservice.organization.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.constraint.DataView;
import org.bcbs.microservice.constraint.Gender;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
class PersonalInfo implements Serializable {

    @NotBlank(message = "Invalid name of account.")
    @Length(max = 64)
    @Column(length = 64, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @NotNull(message = "Invalid gender of account.")
    @Enumerated(value = EnumType.STRING)
    @Column(length = 6, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private Gender gender;

    @Length(max = 250)
    @Column(length = 250)
    @JsonView(value = {DataView.BasicView.class})
    private String avatar;

    // Getter & setter.
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
}
