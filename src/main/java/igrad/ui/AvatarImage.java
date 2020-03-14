package igrad.ui;

import java.util.logging.Logger;

import igrad.commons.core.LogsCenter;
import javafx.scene.image.Image;

/**
 * An UI component that displays the image of the {@code Avatar}.
 */
public class AvatarImage extends Image {

    private static final Logger logger = LogsCenter.getLogger(AvatarImage.class);
    private String imgPath;
    private String avatarName;
    private int rowIndex;
    private int colIndex;

    public AvatarImage(String imgPath) {
        super(imgPath);

        logger.info("HERE HERE" + imgPath);
        this.imgPath = imgPath;
    }

    public AvatarImage(String imgPath, String avatarName, int rowIndex, int colIndex) {
        super(imgPath);

        this.imgPath = imgPath;
        this.avatarName = avatarName;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     * Returns the row index of an image in a grid
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Returns the column index of an image in a grid
     */
    public int getColIndex() {
        return colIndex;
    }

    public String getAvatarName() {
        return this.avatarName;
    }
}
