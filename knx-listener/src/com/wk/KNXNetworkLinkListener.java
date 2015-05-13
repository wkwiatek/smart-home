package com.wk;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import tuwien.auto.calimero.CloseEvent;
import tuwien.auto.calimero.FrameEvent;
import tuwien.auto.calimero.link.event.NetworkLinkListener;

public class KNXNetworkLinkListener implements NetworkLinkListener {

    private DBCollection coll;

    KNXNetworkLinkListener(){
        try {
            MongoClient mongoClient = new MongoClient();
            DB db = mongoClient.getDB("knx");
            coll = db.getCollection("events");
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void confirmation(FrameEvent arg0) {}

    @Override
    public void indication(FrameEvent arg0) {
        System.out.println("src address " + arg0.getSource());
        System.out.println(arg0.getSource().getClass());
        System.out.println("target address " + ((tuwien.auto.calimero.cemi.CEMILData)arg0.getFrame()).getDestination());

        BasicDBObject doc = new BasicDBObject("srcAddress", arg0.getSource())
                .append("targetAddress", ((tuwien.auto.calimero.cemi.CEMILData)arg0.getFrame()).getDestination());
        coll.insert(doc);
    }

    @Override
    public void linkClosed(CloseEvent arg0) {}
}
