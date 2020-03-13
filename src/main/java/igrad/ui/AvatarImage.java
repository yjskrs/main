package igrad.ui;

import javafx.scene.image.Image;

public class AvatarImage extends Image {

    String imgPath;
    int rowIndex;
    int colIndex;

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

}
