package com.ekalips.hakatonproject.events;

import java.io.File;

/**
 * Created by wldev on 4/3/17.
 */

public class TakePictureFromCameraEvent {
   File file;
    public TakePictureFromCameraEvent(File exitImageFile) {
        this.file = exitImageFile;
    }

    public File getFile() {
        return file;
    }
}
