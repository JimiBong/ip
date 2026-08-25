import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTime {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy HHmm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MM yyyy");

    public static String format(LocalDateTime dateTime){
        if (dateTime.toLocalTime().equals(LocalTime.MIDNIGHT)) {
            return dateTime.format(DATE_FORMATTER);
        }
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    public static LocalDateTime parse(String input) throws PennyException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyyy HHmm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        try {
            return LocalDateTime.parse(input.trim(), dateTimeFormatter);
        } catch (DateTimeParseException e1) {
            try {
                LocalDate date = LocalDate.parse(input.trim(), dateFormatter);
                return date.atStartOfDay(); // Midnight as the default time
            } catch (DateTimeParseException e2) {
                throw new PennyException("Use 'ddMMyyyy HHmm' or 'ddMMyyyy'.");
            }
        }
    }
}
