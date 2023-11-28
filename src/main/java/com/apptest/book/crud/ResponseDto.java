package com.apptest.book.crud;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto <T> {
    private String message;
    private int code;
    private boolean success;

    private List<ErrorDto>errors;

    private T data;
}
