package igrad.storage;

import igrad.commons.core.LogsCenter;
import igrad.model.avatar.Avatar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Storage class for storing the {@code Avatar} name
 */
public class AvatarStorage {

    private static final Logger logger = LogsCenter.getLogger(AvatarStorage.class);

    /**
     * Reads the name of a saved {@code Avatar} from file
     */
    public static Avatar readAvatar() throws FileNotFoundException {

        try {
            Path courseBookFilePath = Paths.get("data", "avatar.txt");
            File myObj = new File(courseBookFilePath.toString());
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            myReader.close();

            return new Avatar(data);
        } catch (FileNotFoundException e) {
            logger.warning("Saved avatar not found");
            throw e;
        }
    }

    /**
     * Saves an avatar to file
     */
    public static void writeAvatar(Avatar avatar) {

        try {
            Path courseBookFilePath = Paths.get("data", "avatar.txt");
            FileWriter myWriter = new FileWriter(courseBookFilePath.toString());
            myWriter.write(avatar.getName());
            myWriter.close();
        } catch (IOException e){
            logger.warning("Could not save avatar");
        }

    }

}
