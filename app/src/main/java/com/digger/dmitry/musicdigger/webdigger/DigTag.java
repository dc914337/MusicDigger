package com.digger.dmitry.musicdigger.webdigger;

/**
 * Created by Dmitry on 17/05/2016.
 */
public class DigTag extends DigRequestItem {

    private final String tag;

    public DigTag(String tagStr) {
        super(DigRequestItemType.Tag);
        this.tag = tagStr;
    }

    @Override
    public String toString()
    {
        return tag;
    }

}
