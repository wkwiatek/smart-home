package com.wk;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import tuwien.auto.calimero.DetachEvent;
import tuwien.auto.calimero.process.ProcessEvent;
import tuwien.auto.calimero.process.ProcessListenerEx;


/**
 * Class used to capture KNX events from the EIBD server
 *
 */
public class KNXProcessListener extends ProcessListenerEx {

    private DBCollection coll;

    KNXProcessListener(){
        try {
            MongoClient mongoClient = new MongoClient();
            DB db = mongoClient.getDB("knx");
            coll = db.getCollection("events");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void processEvent(ProcessEvent event, String type){
        String destAddr = event.getDestination().toString();

        // Message value (state)
        byte[] asdu = event.getASDU();
        // Check whether state was correct
        int state = ((asdu != null) && (asdu.length > 0)) ? asdu[0] : -1;

        BasicDBObject doc = new BasicDBObject("address", destAddr)
                .append("state", state)
                .append("type", type);
        coll.insert(doc);

        System.out.println("Group address is " + destAddr + " and the state is " + state);
    }

    @Override
    /**
     * Callback method whenever something is written in the KNX network
     * @param event Contains the information about the event occurred
     */
    public void groupWrite(ProcessEvent event) {
        processEvent(event, "write");
    }

    @Override
    /**
     * Indicates that a KNX group read response message was received from the KNX network.
     *
     * @param event Contains the information about the event occurred
     */
    public void groupReadResponse(ProcessEvent event){
        processEvent(event, "readResponse");
    }

    /**
     * Indicates that a KNX group read request message was received from the KNX network.
     *
     * @param event Contains the information about the event occurred
     */
    public void groupReadRequest(ProcessEvent event){
        processEvent(event, "readRequest");
    }

    @Override
    public void detached(DetachEvent arg0) {
        System.out.println("The KNXNetworkLinkIP has been disconnected from the Process Monitor");
    }
}

