package igrad.ui;

import java.util.logging.Logger;

import igrad.commons.core.LogsCenter;
import javafx.scene.image.Image;

/**
 * An UI component that displays information of {@code Avatar}.
 */
public class AvatarImage extends Image {

    private static final Logger logger = LogsCenter.getLogger(AvatarImage.class);
    private String imgPath;
    private int rowIndex;
    private int colIndex;

    public AvatarImage(String imgPath) {
        super(imgPath);

        logger.info("HERE HERE" + imgPath);
        this.imgPath = imgPath;
    }

    public AvatarImage(String imgPath, int rowIndex, int colIndex) {
        super(imgPath);

        this.imgPath = imgPath;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }
}
