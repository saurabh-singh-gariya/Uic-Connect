package com.example.uicaddressbook.Model;

import java.io.Serializable;

public class Pdf implements Serializable {
    private int mPdfId;
    private String mFileName;
    private String mFileUrl;

    public Pdf(int id, String fileName, String fileUrl) {
        this.mPdfId = id;
        this.mFileName = fileName;
        this.mFileUrl = fileUrl;
    }

    public int getPdfId() {
        return mPdfId;
    }

    public String getFileName() {
        return mFileName;
    }

    public String getFileUrl() {
        return mFileUrl;
    }
}
