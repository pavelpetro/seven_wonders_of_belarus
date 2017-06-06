package by.tut.pavelpetro.sevenwondersbelarus;

import android.net.Uri;

/**
 * The class <code>Wonder</code> is used for storing data about each wonder. It has name, description
 */

class Wonder {

    private String name;
    private String description;
    private int imageId;
    private Uri uriEn;
    private Uri uriBe;
    private Uri uriRu;
    private boolean liked = false;
    private boolean bigImage  = false;
    private String geoString;

    String getGeoString() {
        return geoString;
    }

    void setGeoString(String geoString) {
        this.geoString = geoString;
    }

    boolean isBigImage() {
        return bigImage;
    }

    void setBigImage(boolean bigImage) {
        this.bigImage = bigImage;
    }

    Uri getUriEn() {
        return uriEn;
    }

    void setUriEn(Uri uriEn) {
        this.uriEn = uriEn;
    }

    Uri getUriBe() {
        return uriBe;
    }

    void setUriBe(Uri uriBe) {
        this.uriBe = uriBe;
    }

    Uri getUriRu() {
        return uriRu;
    }

    void setUriRu(Uri uriRu) {
        this.uriRu = uriRu;
    }

    boolean isLiked() {
        return liked;
    }

    void setLiked(boolean liked) {
        this.liked = liked;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    int getImageId() {
        return imageId;
    }

    void setImageId(int imageId) {
        this.imageId = imageId;
    }
}

