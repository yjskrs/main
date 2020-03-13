package igrad.model.avatar;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;
import java.util.ArrayList;
import java.util.List;

public class Avatar {

    public static final String MESSAGE_CONSTRAINTS =
        "The avatar chosen should be in the list of avatars!";
    private static String[] avatarNames = new String[] {
        "po",
        "shibu",
        "chikin",
        "grizzly",
        "koala",
        "frogger",
        "sample"
    };
    public boolean isPlaceholder;
    private String name;

    public Avatar(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
        this.isPlaceholder = false;
    }

    public static boolean isValidName(String test) {
        for (String name : avatarNames) {
            if (name.equals(test)) {
                return true;
            }
        }

        return false;
    }

    public static Avatar getPlaceholderAvatar() {
        Avatar placeholderAvatar = new Avatar("sample");
        placeholderAvatar.isPlaceholder = true;

        return placeholderAvatar;
    }

    public static List<Avatar> getAvatarList() {

        List<Avatar> avatarList = new ArrayList<>();

        for (String name : avatarNames) {
            avatarList.add(
                new Avatar(name)
            );
        }

        return avatarList;
    }

    public String getName() {
        return this.name;
    }

}
