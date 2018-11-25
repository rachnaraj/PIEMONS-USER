package com.hfad.databaseapp;

public class UploadTo {
    private String mName;
    private String mImageUri;

    public UploadTo() {

        //cannot be deleted.. it is needed.
    }

    public UploadTo(String name, String imageUrl) {
        if (name.trim().equals("")) {
            name = "No name";
        }
        mName = name;
        mImageUri = imageUrl;

    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getImageUrl() {
        return mImageUri;

    }

    public void setImageUri(String imageUrl) {
        mImageUri = imageUrl;
    }
}
