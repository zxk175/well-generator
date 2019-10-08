package com.zxk175.generator.enums;

/**
 * @author zxk175
 * @since 2019-10-08 14:30
 */
public enum PathType {

    // 0-项目根路径 1-1级父目录 2-2级父目录
    CURRENT(0),
    ONE(1),
    TWO(2),
    ;

    private int value;


    PathType(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }
}
