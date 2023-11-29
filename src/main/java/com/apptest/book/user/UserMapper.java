package com.apptest.book.user;

import com.apptest.book.book.BookMapper;
import org.mapstruct.*;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public abstract class UserMapper {
    protected BookMapper bookMapper;

    @Mapping(ignore = true, target = "books")
    public abstract UserDto toDto(User user);

    @Mapping(target = "books", expression = "java(user.getBooks().stream().map(this.bookMapper::toDto).toList())")
    public abstract UserDto toDtoWithBook(User user);


    @Mapping(ignore = true, target = "id")
    @Mapping(ignore = true, target = "createdAt")
    @Mapping(ignore = true, target = "updatedAt")
    @Mapping(ignore = true, target = "deletedAt")
    @Mapping(ignore = true, target = "books")
    public abstract User toEntity(UserDto dto);

    @Mapping(ignore = true, target = "books")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget User user, UserDto dto);

    public void view(UserDto dto, User user) {
        dto.setBooks(user.getBooks().stream().map(this.bookMapper::toDto).toList());
    }
}
