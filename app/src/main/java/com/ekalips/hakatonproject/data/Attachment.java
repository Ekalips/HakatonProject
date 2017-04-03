package com.ekalips.hakatonproject.data;

import android.net.Uri;

import java.io.File;

/**
 * Created by wldev on 4/3/17.
 */

public class Attachment {
    private File file;
    private Uri uri;

    public Attachment() {
    }

    public Attachment(Uri uri, File file) {
        this.file = file;
        this.uri = uri;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
