package igrad.model.avatar;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import igrad.storage.AvatarStorage;

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

    private boolean isSample;
    private String name;

    public Avatar(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);

        this.name = name;
        this.isSample = false;
    }

    /**
     * Returns true if a valid avatar name as per the names in AVATAR_NAMES.
     */
    public static boolean isValidName(String name) {
        for (String avatarName : AVATAR_NAMES) {
            if (name.equals(avatarName)) {
                return true;
            }
        }

        return false;
    }

    public static Avatar getAvatar() {
        try {
            return AvatarStorage.readAvatar();
        } catch (FileNotFoundException e) {
            return getSampleAvatar();
        }

    }

    private static Avatar getSampleAvatar() {
        Avatar sampleAvatar = new Avatar("sample");
        sampleAvatar.isSample = true;

        return sampleAvatar;
    }

    public static List<Avatar> getAvatarList() {
        List<Avatar> avatarList = new ArrayList<>();

        for (String name : AVATAR_NAMES) {
            avatarList.add(new Avatar(name));
        }

        return avatarList;
    }

    public boolean getIsSample() {
        return isSample;
    }

    public String getName() {
        return this.name;
    }

}
