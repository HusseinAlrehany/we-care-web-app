package clinicmangement.com.We_Care.validation.imgcustomvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImgFileValidator.class)
public @interface ImgValidation {


    String message() default "Invalid image file";
    Class<?>[] groups() default {}; //for validation groups

    Class<? extends Payload>[] payload() default {}; //payload for metadata
}
