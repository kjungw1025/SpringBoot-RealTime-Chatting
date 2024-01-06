package com.RealTime.Chatting.global.error.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class FieldErrorResult {
    private final String name;
    private final String error;
}
