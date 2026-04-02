package edu.eci.dosw.DOSW_Library.core.validator;

import edu.eci.dosw.DOSW_Library.core.model.User;
import edu.eci.dosw.DOSW_Library.core.util.ValidationUtil;

public class UserValidator {
    private UserValidator() {}

    public static void validate(String name, String id) {
        ValidationUtil.validateNotEmpty(name, "name");
        ValidationUtil.validateNotEmpty(id, "id");
    }

    public static void validate(User user) {
        ValidationUtil.validateNotNull(user, "user");
        ValidationUtil.validateNotEmpty(user.getName(), "name");
        ValidationUtil.validateNotEmpty(user.getId(), "id");
    }
}