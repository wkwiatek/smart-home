package com.wk;

import tuwien.auto.calimero.exception.KNXException;
import tuwien.auto.calimero.knxnetip.KNXnetIPConnection;
import tuwien.auto.calimero.link.KNXNetworkLinkIP;
import tuwien.auto.calimero.link.event.NetworkLinkListener;
import tuwien.auto.calimero.link.medium.TPSettings;
import tuwien.auto.calimero.process.ProcessCommunicatorImpl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

public class Main {

    public static KNXNetworkLinkIP netLinkIp;
    public static InetSocketAddress local;
    public static InetSocketAddress remote;
    private static KNXProcessListener processListener;
    private static NetworkLinkListener networkListener;

    public static void main(String[] args) {

        try {

            try {
                processListener = new KNXProcessListener();
                networkListener = new KNXNetworkLinkListener();
                local = new InetSocketAddress(InetAddress.getLocalHost(), 0);
                remote = new InetSocketAddress("192.168.1.21", KNXnetIPConnection.IP_PORT);
            } catch (UnknownHostException e) {
                System.out.println("There was a problem trying to obtain the local host address, aborting.");
            }

            netLinkIp = new KNXNetworkLinkIP(
                    KNXNetworkLinkIP.TUNNEL,
                    local,
                    remote,
                    false,
                    TPSettings.TP1
            );

            System.out.println("--- Connected ---");

            new ProcessCommunicatorImpl(netLinkIp)
                    .addProcessListener(processListener);

            netLinkIp.addLinkListener(networkListener);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (KNXException e) {
            e.printStackTrace();
        } finally {
            if (netLinkIp != null) {
                netLinkIp.close();
                System.out.println("Connection closed");
            }
        }
    }
}
