package com.example.dmitry.musicdigger.webdigger;

import android.content.Context;
import android.content.res.Resources;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Dmitry on 17/05/2016.
 */
public class DigRequestConstructor {

    DigTag[] tags;

    ArrayList<DigRequestItem> requestItems=new ArrayList();
    ArrayList<DigTag> includedItems=new ArrayList();
    ArrayList<DigTag> excludedItems=new ArrayList();


    public DigRequestConstructor(Context ctx)
    {
        try {
            loadTags(ctx);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAnd()
    {
        requestItems.add(new DigRequestItem(DigRequestItemType.And));
    }

    public DigRequestConstructor addIncludeTag(DigTag tag)
    {
        includedItems.add(tag);
        return this;
    }

    public DigRequestConstructor addExcludeTag(DigTag tag)
    {
        excludedItems.add(tag);
        return this;
    }

    public DigRequestConstructor addNot()
    {
        requestItems.add(new DigRequestItem(DigRequestItemType.Not));
        return this;
    }

    public DigRequestConstructor removeLast()
    {
        if(requestItems.size()>0)
        {
            requestItems.remove(requestItems.size()-1);
        }
        return this;
    }



    public DigTag[] getAllTags()
    {
       return tags;
    }


    private void loadTags(Context ctx) throws IOException {
        InputStream iS;
        Resources resources = ctx.getResources();
        int rID = resources.getIdentifier("tags", "raw", ctx.getPackageName());
        //get the file as a stream
        iS = resources.openRawResource(rID);

        //create a buffer that has the same size as the InputStream
        byte[] buffer = new byte[iS.available()];
        //read the text file as a stream, into the buffer
        iS.read(buffer);
        //create a output stream to write the buffer into
        ByteArrayOutputStream oS = new ByteArrayOutputStream();
        //write this buffer to the output stream
        oS.write(buffer);
        //Close the Input and Output streams
        oS.close();
        iS.close();

        String[] tagsStr= oS.toString().split("\n");
        tags=new DigTag[tagsStr.length];
        int cnt=0;
        for(String tagStr : tagsStr)
        {
            tags[cnt]=new DigTag(tagStr);
            cnt+=1;
        }
    }


    public boolean canAnd() {
        return requestItems.size()>0 && requestItems.get(requestItems.size()-1).Type==DigRequestItemType.Tag;
    }

    public boolean canRemove() {
        return  requestItems.size()>0;
    }

    public boolean canNot() {
        return requestItems.size()==0 || requestItems.get(requestItems.size()-1).Type==DigRequestItemType.And;
    }

    public boolean canAddTag() {
        return requestItems.size()==0 || requestItems.get(requestItems.size()-1).Type!=DigRequestItemType.Tag;
    }

    @Override
    public String toString()
    {
        StringBuilder sb=new StringBuilder();
        for(DigRequestItem item: requestItems)
        {
            sb.append(item);
        }
        return sb.toString();
    }

    public String excludedToString()
    {
        StringBuilder sb=new StringBuilder("Excluded: ");
        for(DigRequestItem item: excludedItems)
        {
            sb.append(item);
            sb.append(" | ");
        }
        if(!excludedItems.isEmpty())
            return sb.substring(0, sb.length()- 3);
        else
            return sb.toString();
    }

    public String includedToString()
    {
        StringBuilder sb=new StringBuilder("Included: ");
        for(DigRequestItem item: includedItems)
        {
            sb.append(item);
            sb.append(" | ");
        }

        if(!includedItems.isEmpty())
            return sb.substring(0, sb.length()- 3);
        else
            return sb.toString();
    }


    public String getUri()
    {
        StringBuilder sb=new StringBuilder();
        for(DigRequestItem item: includedItems)
        {
            sb.append(item);
            sb.append(" | ");
        }
        for(DigRequestItem item: excludedItems)
        {
            sb.append("~");
            sb.append(item);
            sb.append(" | ");
        }

        String res=sb.substring(0, sb.length() - 3);
      return res;
    }

}
