package com.ndscompany.text.classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(foreignKeys = @ForeignKey(entity = Version.class, parentColumns = "id", childColumns = "versionId"))
public class TestCase implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private long versionId;
    private String description;
    private int state;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getVersionId() {
        return versionId;
    }

    public void setVersionId(long versionId) {
        this.versionId = versionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
        dest.writeLong(this.versionId);
        dest.writeString(this.description);
        dest.writeInt(this.state);
        dest.writeString(this.text);
    }

    public TestCase() {
    }

    protected TestCase(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
        this.versionId = in.readLong();
        this.description = in.readString();
        this.state = in.readInt();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<TestCase> CREATOR = new Parcelable.Creator<TestCase>() {
        @Override
        public TestCase createFromParcel(Parcel source) {
            return new TestCase(source);
        }

        @Override
        public TestCase[] newArray(int size) {
            return new TestCase[size];
        }
    };
}
