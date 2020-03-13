package igrad.ui;

import javafx.scene.image.Image;

/**
 * Image of the Avatar
 */
public class AvatarImage extends Image {

    private String imgPath;
    private int rowIndex;
    private int colIndex;

    public AvatarImage(String imgPath) {
        super(imgPath);
        System.out.println("HERE HERE " + imgPath);
        this.imgPath = imgPath;
    }

    public AvatarImage(String imgPath, int rowIndex, int colIndex) {
        super(imgPath);
        this.imgPath = imgPath;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     * returns the row index of an image in a grid
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * returns the column index of an image in a grid
     */
    public int getColIndex() {
        return colIndex;
    }

}
