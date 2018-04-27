package org.bcbs.microservice.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import org.bcbs.microservice.constant.DataView;
import org.bcbs.microservice.constant.Gender;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class AccountEntity implements Serializable {

    @NotEmpty(message = "Invalid phone number of account.")
    @Length(min = 11, max = 11)
    @Column(length = 11, nullable = false, unique = true)
    @JsonView(value = {DataView.BasicView.class})
    private String phoneNo;

    @NotEmpty(message = "Invalid password of account.")
    @Length(max = 64)
    @Column(length = 64, nullable = false)
    @JsonView(value = {DataView.ExtensiveView.class})
    private String password;

    @NotEmpty(message = "Invalid name of account.")
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
}
