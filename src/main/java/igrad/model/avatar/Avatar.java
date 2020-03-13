package igrad.model.avatar;

public class Avatar {

    private String imgPath;
    public boolean isPlaceholder;

    public Avatar( String imgPath ){
        this.imgPath = imgPath;
    }

    public String getImgPath(){
        return this.imgPath;
    }

    public static Avatar getPlaceholderAvatar(){
        Avatar placeholderAvatar = new Avatar("/avatars/sample.png");
        placeholderAvatar.isPlaceholder = true;

        return placeholderAvatar;
    }

}
