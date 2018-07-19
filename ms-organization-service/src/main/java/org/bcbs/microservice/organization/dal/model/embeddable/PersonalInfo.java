package org.bcbs.microservice.organization.dal.model.embeddable;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.common.constraint.DataView;
import org.bcbs.microservice.common.constraint.Gender;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class PersonalInfo implements Serializable {

    @NotBlank(message = "Invalid name.")
    @Length(max = 64)
    @Column(length = 64, nullable = false)
    @JsonView(value = {DataView.BasicView.class})
    private String name;

    @NotNull(message = "Invalid gender.")
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
