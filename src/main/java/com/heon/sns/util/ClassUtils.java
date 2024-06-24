package com.heon.sns.util;

import java.util.Optional;

public class ClassUtils {

    // clazz가 null이 아니고, clazz가 instance라면 캐스팅을 해주고, 아니면 그냥 empty를 반환해주는 함수
    public static <T> Optional<T> getSafeCaseInstance(Object o, Class<T> clazz){
        return clazz != null && clazz.isInstance(o) ? Optional.of(clazz.cast(o)) : Optional.empty();
    }

}
