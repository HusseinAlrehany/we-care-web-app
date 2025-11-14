package clinicmangement.com.WeCare.enums;

import clinicmangement.com.WeCare.exceptions.types.InvalidInputException;

import java.util.Arrays;

public enum VisitStatus {
    BOOKED,
    CHECKED,
    CANCELED;


    public static VisitStatus fromStringToVisitEnum(String visitStatus){
        return Arrays.stream(VisitStatus.values())
                .filter(status-> status.name().equalsIgnoreCase(visitStatus))
                .findFirst()
                .orElseThrow(()-> new InvalidInputException("Invalid Visit Status"));
    }
}
