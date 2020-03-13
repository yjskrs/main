package igrad.model.avatar;

import static igrad.commons.util.AppUtil.checkArgument;
import static java.util.Objects.requireNonNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import igrad.commons.core.LogsCenter;
import igrad.storage.AvatarStorage;

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

    private boolean isSample;
    private String name;

    public Avatar(String name) {
        requireNonNull(name);
        checkArgument(isValidName(name), MESSAGE_CONSTRAINTS);
        this.name = name;
        this.isSample = false;
    }

    /**
     * Checks if name is in the list of valid Avatar names
     */
    public static boolean isValidName(String test) {
        for (String name : avatarNames) {
            if (name.equals(test)) {
                return true;
            }
        }

        return false;
    }

    public boolean getIsSample() {
        return isSample;
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
