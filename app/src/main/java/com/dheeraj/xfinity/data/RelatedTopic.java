package com.dheeraj.xfinity.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class RelatedTopic {

    public String Result;
    public String FirstURL;
    @PrimaryKey @NonNull
    public String Text = "Text";
    @Embedded(prefix = "icon_")
    public Icon Icon;
}
