package com.digger.dmitry.musicdigger.webdigger;

/**
 * Created by Dmitry on 17/05/2016.
 */
public class DigRequestItem {

    public DigRequestItemType Type;

    public DigRequestItem(DigRequestItemType type)
    {
        Type=type;
    }

    public String toString()
    {
        switch (Type)
        {
            case And:
                return " | ";
            case Not:
                return "~";
        }
        return "";
    }

}
