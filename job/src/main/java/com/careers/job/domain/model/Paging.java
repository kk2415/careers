package com.careers.job.domain.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Objects;

public record Paging(
        Integer page,
        Integer size
) {

    public static Paging of(
            Integer page,
            Integer size
    ) {
        return new Paging(
                page,
                size
        );
    }

    public boolean pageable() {
        return Objects.nonNull(size) && size > 0;
    }

    public static boolean pageable(Integer size) {
        return Objects.nonNull(size) && size > 0;
    }

    public Pageable createPageable() {
        if (Objects.isNull(page) && Objects.isNull(size)) {
            return null;
        } else if (Objects.isNull(page)) {
            return PageRequest.ofSize(size);
        } else {
            return PageRequest.of(page, size);
        }
    }

    public static Pageable createPageable(
            Integer page,
            Integer size
    ) {
        if (Objects.isNull(page) && Objects.isNull(size)) {
            return null;
        } else if (Objects.isNull(page)) {
            return PageRequest.ofSize(size);
        } else {
            return PageRequest.of(page, size);
        }
    }
}
