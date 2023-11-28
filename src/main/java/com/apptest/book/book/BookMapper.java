package com.apptest.book.book;

import org.mapstruct.*;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    public abstract BookDto toDto(Book book);

    @Mapping(ignore = true, target = "published")
    @Mapping(ignore = true, target = "deletedAt")
    public abstract Book toEntity(BookDto dto);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Book book, BookDto dto);
}
