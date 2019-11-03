package com.rabbit.entity;

import lombok.Data;

/**
 * @author gyc
 * @date 2019/11/2
 */

@Data
public class CommandEntity<T> {

    private CommandType commandType;

    private T Object;




}
