package com.apptest.book.book;

import com.apptest.book.crud.ResponseDto;
import com.apptest.book.crud.SimpleCrud;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController implements SimpleCrud<Integer, BookDto> {
    private final BookService bookService;

    @Override
    @PostMapping("/create")
    public ResponseDto<BookDto> create(@RequestBody @Valid BookDto dto) {
        return this.bookService.create(dto);
    }

    @Override
    @GetMapping("/get")
    public ResponseDto<BookDto> get(@RequestParam Integer id) {
        return this.bookService.get(id);
    }

    @Override
    @PutMapping("/update")
    public ResponseDto<BookDto> update(@RequestBody @Valid BookDto dto, @RequestParam Integer id) {
        return this.bookService.update(dto, id);
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseDto<BookDto> delete(@RequestParam Integer id) {
        return this.bookService.delete(id);
    }

    @Override
    @GetMapping("/getAll")
    public ResponseDto<List<BookDto>> getAll() {
        return this.bookService.getAll();
    }
}
