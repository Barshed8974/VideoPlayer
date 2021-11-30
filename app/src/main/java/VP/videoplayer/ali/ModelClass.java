package VP.videoplayer.ali;

import android.graphics.Bitmap;

public class ModelClass {
    private String name;
    private String videoPath;
    private Bitmap thumbnail;

    public ModelClass(String name, String videoPath, Bitmap thumbnail) {
        this.name = name;
        this.videoPath = videoPath;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }
}
