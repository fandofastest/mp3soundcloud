package ModalClass;

/**
 * Created by Remmss on 28-08-2017.
 */

public class MusicModalClass {

    private String musicName;
    private String musicYear;
    private int img;

    public MusicModalClass(int img,String musicName, String musicYear) {
        this.musicName = musicName;
        this.musicYear = musicYear;
        this.img = img;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicYear() {
        return musicYear;
    }

    public void setMusicYear(String musicYear) {
        this.musicYear = musicYear;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}