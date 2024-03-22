package com.ll.sb20240322.standard.util;

import com.ll.sb20240322.global.app.AppConfig;
import lombok.SneakyThrows;

public class Ut {
    public static class json {
        @SneakyThrows
        public static String toString(Object obj) {
            return AppConfig.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }
    }
}