package com.apptest.book.book;

import com.apptest.book.crud.ErrorDto;
import com.apptest.book.crud.ResponseDto;
import com.apptest.book.crud.SimpleCrud;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements SimpleCrud<Integer, BookDto> {

    private final BookValidation bookValidation;
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public ResponseDto<BookDto> create(BookDto dto) {
        List<ErrorDto> errors = this.bookValidation.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<BookDto>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Book book = this.bookMapper.toEntity(dto);
            book.setPublished(LocalDate.now());
            this.bookRepository.save(book);

            return ResponseDto.<BookDto>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.bookMapper.toDto(book))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<BookDto>builder()
                    .code(-1)
                    .message("Type while saving error")
                    .build();
        }
    }

    @Override
    public ResponseDto<BookDto> get(Integer id) {
        try {
            return this.bookRepository.findByIdAndDeletedAtIsNull(id)
                    .map(book -> ResponseDto.<BookDto>builder()
                            .success(true)
                            .message("Ok")
                            .data(this.bookMapper.toDto(book))
                            .build())
                    .orElse(ResponseDto.<BookDto>builder()
                            .code(-1)
                            .message("type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<BookDto>builder()
                    .code(-1)
                    .message("Type while getting error")
                    .build();
        }
    }

    @Override
    public ResponseDto<BookDto> update(BookDto dto, Integer id) {
        List<ErrorDto> errors = this.bookValidation.validate(dto);
        if (!errors.isEmpty()) {
            return ResponseDto.<BookDto>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            return this.bookRepository.findByIdAndDeletedAtIsNull(id)
                    .map(book -> {
                        this.bookRepository.save(book);
                        this.bookMapper.update(book, dto);

                        return ResponseDto.<BookDto>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.bookMapper.toDto(book))
                                .build();
                    })
                    .orElse(ResponseDto.<BookDto>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<BookDto>builder()
                    .code(-1)
                    .message("type while updating error")
                    .build();
        }
    }

    @Override
    public ResponseDto<BookDto> delete(Integer id) {
        try {
            return this.bookRepository.findByIdAndDeletedAtIsNull(id)
                    .map(book -> {
                        book.setDeletedAt(LocalDate.now());
                        this.bookRepository.save(book);

                        return ResponseDto.<BookDto>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.bookMapper.toDto(book))
                                .build();
                    })
                    .orElse(ResponseDto.<BookDto>builder()
                            .code(-1)
                            .message("Type is not found")
                            .build());

        } catch (Exception e) {
            return ResponseDto.<BookDto>builder()
                    .code(-1)
                    .message("Type while deleting error")
                    .build();
        }
    }

    @Override
    public ResponseDto<List<BookDto>> getAll() {
        try {
            List<Book> categories = this.bookRepository.getAllBooks();
            if (categories.isEmpty()) {
                return ResponseDto.<List<BookDto>>builder()
                        .code(-1)
                        .message("Types are not found")
                        .build();
            }
            return ResponseDto.<List<BookDto>>builder()
                    .success(true)
                    .message("Ok")
                    .data(categories.stream().map(this.bookMapper::toDto).toList())
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<BookDto>>builder()
                    .code(-1)
                    .message("Types while getting all")
                    .build();
        }
    }
}
