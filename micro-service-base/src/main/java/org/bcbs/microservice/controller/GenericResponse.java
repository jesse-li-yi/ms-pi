package org.bcbs.microservice.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Value;
import org.bcbs.microservice.constant.DataView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Value
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class GenericResponse {

    @JsonView(value = {DataView.BasicView.class})
    private Date timestamp = new Date();

    @JsonView(value = {DataView.BasicView.class})
    private int status;

    @JsonView(value = {DataView.BasicView.class})
    private Object data;

    @JsonView(value = {DataView.BasicView.class})
    private String message;

    private <T> GenericResponse(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> GenericResponse success(@NonNull T data) {
        return new GenericResponse(HttpStatus.OK.value(), data, null);
    }

    public static <T> GenericResponse success(@NonNull Collection<T> data) {
        return new GenericResponse(HttpStatus.OK.value(), data, null);
    }

    public static <T> GenericResponse success(@NonNull Page<T> data) {
        return new GenericResponse(HttpStatus.OK.value(), new ViewPage<>(data), null);
    }

    public static GenericResponse failure(String message) {
        return failure(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static GenericResponse failure(int status, String message) {
        return new GenericResponse(status, null, message);
    }

    private static class ViewPage<T> extends PageImpl<T> {

        ViewPage(final Page<T> page) {
            super(page.getContent(), page.getPageable(), page.getTotalElements());
        }

        @Override
        @JsonView(value = {DataView.BasicView.class})
        public long getTotalElements() {
            return super.getTotalElements();
        }

        @Override
        @JsonView(value = {DataView.BasicView.class})
        public int getSize() {
            return super.getSize();
        }

        @Override
        @JsonView(value = {DataView.BasicView.class})
        public int getTotalPages() {
            return super.getTotalPages();
        }

        @Override
        @JsonView(value = {DataView.BasicView.class})
        public int getNumber() {
            return super.getNumber();
        }

        @Override
        @NonNull
        @JsonView(value = {DataView.BasicView.class})
        public List<T> getContent() {
            return super.getContent();
        }
    }
}