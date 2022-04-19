package com.pete.apps.dailytweetsimageuploader;

public class Upload {
    private  String mName;
    private  String mImageUri;


    public Upload(){

    }
    public Upload(String name, String imageUri){

        if(name.trim().equals("")){
            name= "No name";
        }

        this.mName= name;
        this.mImageUri=imageUri;
    }

    public String getmName() {
        return mName;
    }

    public String getmImageUri() {
        return mImageUri;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setmImageUri(String mImageUri) {
        this.mImageUri = mImageUri;
    }

}
