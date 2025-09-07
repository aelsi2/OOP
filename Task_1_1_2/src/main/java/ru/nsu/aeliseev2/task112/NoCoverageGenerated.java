package ru.nsu.aeliseev2.task112;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Disables test coverage for a type or a method.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface NoCoverageGenerated {
    /**
     * A human-readable reason for disabling test coverage.
     *
     * @return The reason.
     */
    String reason() default "";
}
