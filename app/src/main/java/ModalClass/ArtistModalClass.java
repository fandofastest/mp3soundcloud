package ModalClass;

/**
 * Created by Remmss on 29-08-2017.
 */

public class ArtistModalClass {

    int img;
    String ArtistName;
    String TotalSongs;

    public ArtistModalClass(int img, String artistName, String totalSongs) {
        this.img = img;
        ArtistName = artistName;
        TotalSongs = totalSongs;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getArtistName() {
        return ArtistName;
    }

    public void setArtistName(String artistName) {
        ArtistName = artistName;
    }

    public String getTotalSongs() {
        return TotalSongs;
    }

    public void setTotalSongs(String totalSongs) {
        TotalSongs = totalSongs;
    }
}
