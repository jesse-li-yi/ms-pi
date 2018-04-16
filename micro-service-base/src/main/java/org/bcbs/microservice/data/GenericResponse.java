package org.bcbs.microservice.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.bcbs.microservice.data.view.InternalJsonPage;
import org.bcbs.microservice.data.view.InternalView;
import org.bcbs.microservice.data.view.PublicJsonPage;
import org.bcbs.microservice.data.view.PublicView;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class GenericResponse {

    @JsonView(PublicView.class)
    private Date timestamp = new Date();

    @JsonView(PublicView.class)
    private int status;

    @JsonView(PublicView.class)
    private Object data;

    @JsonView(PublicView.class)
    private String message;

    private <T> GenericResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> GenericResponse success(T data) {
        return new GenericResponse(HttpStatus.OK.value(), data, null);
    }

    public static <T> GenericResponse success(List<T> data) {
        return new GenericResponse(HttpStatus.OK.value(), data, null);
    }

    public static <T> GenericResponse success(Page<T> data) {
        return success(data, PublicView.class);
    }

    public static <T> GenericResponse success(Page<T> data, Class<?> viewClass) {
        return new GenericResponse(HttpStatus.OK.value(), viewClass == InternalView.class ?
                new InternalJsonPage<>(data) : new PublicJsonPage<>(data), null);
    }

    public static GenericResponse failure(String message) {
        return failure(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static GenericResponse failure(int status, String message) {
        return new GenericResponse(status, null, message);
    }
}
