package com.weimok.common.exception;

import com.weimok.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author itsNine
 * @create 2020-03-17-15:00
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MallException extends RuntimeException{

    private ExceptionEnum exceptionEnum;
}
