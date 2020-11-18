package com.wmz.utils.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class EventSignBean implements Parcelable {


    String signFilePath;

    public EventSignBean(String signFilePath) {
        this.signFilePath = signFilePath;
    }

    public String getSignFilePath() {
        return signFilePath;
    }

    public void setSignFilePath(String signFilePath) {
        this.signFilePath = signFilePath;
    }

    @Override
    public String toString() {
        return "EventSignBean{" +
                "signFilePath='" + signFilePath + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.signFilePath);
    }

    public EventSignBean() {
    }

    protected EventSignBean(Parcel in) {
        this.signFilePath = in.readString();
    }

    public static final Creator<EventSignBean> CREATOR = new Creator<EventSignBean>() {
        @Override
        public EventSignBean createFromParcel(Parcel source) {
            return new EventSignBean(source);
        }

        @Override
        public EventSignBean[] newArray(int size) {
            return new EventSignBean[size];
        }
    };
}
