package org.bcbs.microservice.data.view;

import com.fasterxml.jackson.annotation.JsonView;
import io.micrometer.core.lang.NonNullApi;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@NonNullApi
public class PublicJsonPage<T> extends PageImpl<T> {

    public PublicJsonPage(final List<T> content) {
        super(content);
    }

    public PublicJsonPage(final Page<T> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }

    @Override
    @JsonView(PublicView.class)
    public int getTotalPages() {
        return super.getTotalPages();
    }

    @Override
    @JsonView(PublicView.class)
    public long getTotalElements() {
        return super.getTotalElements();
    }

    @Override
    @JsonView(PublicView.class)
    public boolean hasNext() {
        return super.hasNext();
    }

    @Override
    @JsonView(PublicView.class)
    public boolean isLast() {
        return super.isLast();
    }

    @Override
    @JsonView(PublicView.class)
    public boolean hasContent() {
        return super.hasContent();
    }

    @Override
    @JsonView(PublicView.class)
    public List<T> getContent() {
        return super.getContent();
    }
}
