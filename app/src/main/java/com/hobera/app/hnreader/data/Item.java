package com.hobera.app.hnreader.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Hernan Obera on 5/20/2017.
 */

public final class Item implements Parcelable {
    @Retention(RetentionPolicy.SOURCE)
    @StringDef({
            JOB,
            STORY,
            COMMENT,
            POLL
    })
    @interface Type {}
    public static final String JOB = "job";
    public static final String STORY = "story";
    public static final String COMMENT = "comment";
    public static final String POLL = "poll";

    private String by;
    private int descendants;
    @NonNull
    private long id;
    private long[] kids;
    private int score;
    private long time;
    private String title;
    private String type;
    private String url;
    private String parent;
    private String text;
    private boolean deleted;

    private int mRank;

    public Item(@NonNull long id, int mRank, String by, int descendants, long[] kids,
                int score, long time, String title, String type, String url, String parent,
                String text, boolean deleted) {
        this.id = id;
        this.mRank = mRank;
        this.by = by;
        this.descendants = descendants;
        this.kids = kids;
        this.score = score;
        this.time = time;
        this.title = title;
        this.type = type;
        this.url = url;
        this.parent = parent;
        this.text = text;
        this.deleted = deleted;
    }

    protected Item(Parcel in) {
        by = in.readString();
        descendants = in.readInt();
        id = in.readLong();
        kids = in.createLongArray();
        score = in.readInt();
        time = in.readLong();
        title = in.readString();
        type = in.readString();
        url = in.readString();
        parent = in.readString();
        text = in.readString();
        deleted = in.readInt() == 1 ? true : false;
        mRank = in.readInt();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public void populate(String mBy, int mDescendants, long[] mKids, int mScore,
                         long mTime, String mTitle, String mType, String mUrl, String mParent,
                         String text, boolean deleted) {
        this.by = mBy;
        this.descendants = mDescendants;
        this.kids = mKids;
        this.score = mScore;
        this.time = mTime;
        this.title = mTitle;
        this.type = mType;
        this.url = mUrl;
        this.parent = mParent;
        this.text = text;
        this.deleted = deleted;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public int getRank() {
        return mRank;
    }

    public String getBy() {
        return by;
    }

    public int getDescendants() {
        return descendants;
    }

    public long[] getKids() {
        return kids;
    }

    public int getScore() {
        return score;
    }

    public long getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    @Type
    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getParent() {
        return parent;
    }

    public String getText() {
        return text;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(by);
        dest.writeInt(descendants);
        dest.writeLong(id);
        dest.writeLongArray(kids);
        dest.writeInt(score);
        dest.writeLong(time);
        dest.writeString(title);
        dest.writeString(type);
        dest.writeString(url);
        dest.writeString(parent);
        dest.writeString(text);
        dest.writeInt(deleted ? 1 : 0);
        dest.writeInt(mRank);
    }}
