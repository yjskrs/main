package igrad.model.avatar;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Avatar (the user selects to represent him/herself) in the course book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Avatar {

    public static final String MESSAGE_CONSTRAINTS = "The avatar chosen should be in the list of avatars!";
    private static final String[] AVATAR_NAMES = new String[] {
        "po",
        "shibu",
        "chikin",
        "grizzly",
        "koala",
        "frogger",
        "sample"
    };

    private String name;

    // This empty constructor is only required and used for Json serialising during user prefs
    public Avatar() {
    }

    public Avatar(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);

        this.name = name;
    }

    /**
     * Returns true if a valid avatar name as per the names in AVATAR_NAMES.
     */
    public static boolean isValidName(String name) {
        for (String avatarName : AVATAR_NAMES) {
            if (name.equals(avatarName) || name.equals(avatarName + "-sad")) {
                return true;
            }
        }

        return false;
    }

    /*public static Avatar getAvatar() {
        try {
            return AvatarStorage.readAvatar();
        } catch (FileNotFoundException e) {
            return getSampleAvatar();
        }

    }*/

    public static Avatar getSampleAvatar() {
        Avatar sampleAvatar = new Avatar("sample");

        return sampleAvatar;
    }

    public static List<Avatar> getAvatarList() {
        List<Avatar> avatarList = new ArrayList<>();

        for (String name : AVATAR_NAMES) {
            avatarList.add(new Avatar(name));
        }

        return avatarList;
    }

    /**
     * Returns true if both avatars have the same name.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Avatar)) {
            return false;
        }

        Avatar otherAvatar = (Avatar) other;
        return otherAvatar.getName().equals(getName());
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name : " + name);
        return sb.toString();
    }

}
