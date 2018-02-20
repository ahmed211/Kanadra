package com.bit.apps.kanadra.model.RSS_Model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ahmed on 1/16/2018.
 */

@Root(name = "channel", strict = false)
public class Channel {

    @ElementList(inline = true, name="item")
    private List<FeedItem> mFeedItems;

    public List<FeedItem> getFeedItems() {
        return mFeedItems;
    }
}