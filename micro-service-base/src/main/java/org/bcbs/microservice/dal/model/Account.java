package org.bcbs.microservice.dal.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.bcbs.microservice.dal.view.PublicDataView;
import org.bcbs.microservice.data.Gender;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Embeddable
public class Account implements Serializable {

    @NotEmpty
    @Length(min = 11, max = 11)
    @Column(length = 11, nullable = false, unique = true)
    @JsonView(PublicDataView.class)
    private String phoneNo;

    @NotEmpty
    @Length(max = 64)
    @Column(length = 64, nullable = false)
    private String password;

    @NotEmpty
    @Length(max = 64)
    @Column(length = 64, nullable = false)
    @JsonView(PublicDataView.class)
    private String nickname;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 6, nullable = false)
    @JsonView(PublicDataView.class)
    private Gender gender;

    @Length(max = 250)
    @Column(length = 250)
    @JsonView(PublicDataView.class)
    private String avatar;
}
