package com.apptest.book.book;

import com.apptest.book.crud.ErrorDto;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookValidation {

    public List<ErrorDto> validate(BookDto dto) {
        List<ErrorDto> errors = new ArrayList<>();

        if (StringUtils.isBlank(dto.getAuthor())) {
            errors.add(new ErrorDto("author", "author cannot be null or empty"));
        }
        return errors;
    }
}
