package org.bcbs.microservice.data.view;

import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.core.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@NonNullApi
public class InternalJsonPage<T> extends PageImpl<T> {

    public InternalJsonPage(final List<T> content) {
        super(content);
    }

    public InternalJsonPage(final Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    @Override
    @JsonView(InternalView.class)
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @Override
    @JsonView(InternalView.class)
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @Override
    @JsonView(InternalView.class)
    public boolean hasNext() {
        return super.hasNext();
    }

    @Override
    @JsonView(InternalView.class)
    public boolean isLast() {
        return super.isLast();
    }

    @Override
    @JsonView(InternalView.class)
    public boolean hasContent() {
        return super.hasContent();
    }

    @Override
    @JsonView(InternalView.class)
    public List<T> getContent() {
        return super.getContent();
    }
}
