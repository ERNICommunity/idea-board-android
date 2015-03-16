package community.erninet.ch.ideaboard.adapter;

import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validate form inputs
 */
public class FormValidator {

    //regex for email address
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //very simple regex for phone numbers
    private static final String PHONE_PATTERN = "^([0-9\\(\\)\\/\\+ \\-]{10,})$";

    /**
     * Checks whether a username isn't empty
     *
     * @param v textview that should be tested
     * @return true if length is larger than zero, false if empty inout
     */
    public static boolean validateUsername(EditText v) {
        if (v.getText().length() < 5) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether a password has at least 4 characters
     *
     * @param v textview that should be tested
     * @return True if valid, False if not valid
     */
    public static boolean validatePassword(EditText v) {
        if (v.getText().length() < 5) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether an E-Mail address is valid against a regex
     *
     * @param v textview that should be tested
     * @return True if valid, False if not valid
     */
    public static boolean validateEmail(EditText v) {
        Pattern emailPattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = emailPattern.matcher(v.getText().toString());

        if (v.getText().length() != 0 && !matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Checks whether a phone number is valid against a regex
     *
     * @param v textview that should be tested
     * @return True if valid, False if not valid
     */
    public static boolean validatePhone(EditText v) {
        Pattern emailPattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = emailPattern.matcher(v.getText().toString());

        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }
}
