package com.josiassantos.desafio_anota_ai;

import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;

public class AutoDisplayNameGenerator implements DisplayNameGenerator {
    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return formatName(testClass.getSimpleName());
    }

    @Override
    public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
        return formatName(nestedClass.getSimpleName());
    }

    @Override
    public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
        return formatName(testMethod.getName());
    }

    private String formatName(String name) {
        String formatted = name.replace('_', ' ').replaceAll("([a-z])([A-Z])", "$1 $2").toLowerCase();
        if (!formatted.isEmpty())
            formatted = formatted.substring(0, 1).toUpperCase() + formatted.substring(1);

        return formatted;
    }
}
