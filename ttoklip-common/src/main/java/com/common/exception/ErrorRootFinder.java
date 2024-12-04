package com.common.exception;

import com.common.event.Modules;
import java.util.Arrays;

public class ErrorRootFinder {

    public static Modules determineModuleFromException(Throwable e) {
        Throwable rootCause = getRootCause(e);
        return getModules(rootCause);
    }

    private static Modules getModules(final Throwable rootCause) {
        return Arrays.stream(rootCause.getStackTrace())
                .map(StackTraceElement::getClassName)
                .map(ErrorRootFinder::getModuleFromClassName)
                .filter(module -> module != Modules.UNKNOWN)
                .findFirst()
                .orElse(Modules.UNKNOWN);
    }

    private static Throwable getRootCause(Throwable e) {
        Throwable cause = e.getCause();
        if (cause != null && cause != e) {
            return getRootCause(cause);
        } else {
            return e;
        }
    }

    private static Modules getModuleFromClassName(String className) {
        if (className.startsWith(Modules.API.getRoot())) {
            return Modules.API;
        }
        if (className.startsWith(Modules.DOMAIN.getRoot())) {
            return Modules.DOMAIN;
        }
        if (className.startsWith(Modules.INFRASTRUCTURE.getRoot())) {
            return Modules.INFRASTRUCTURE;
        }
        if (className.startsWith(Modules.COMMON.getRoot())) {
            return Modules.COMMON;
        }
        return Modules.UNKNOWN;
    }
}
