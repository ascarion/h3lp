package net.ascarion.h3lp;

import org.jibble.pircbot.*;

import java.util.HashMap;
import java.util.Set;


/**
 * Created by daniel on 07.10.14.
 * This is doing all the stuff.
 */
public class H3lp extends PircBot {

    public static String SERVER = "irc.swc-irc.com";
    public static String NAME = "H3-LP";
    private static String[] CHANNELS = {"#ascarion"};

    private HashMap links;

    /**
     *
     */
    public H3lp() {
        this.links = new HashMap<String, String>();
        this.addLink(new String[][]{{"faq", "http://commcent.swc-empire.com/blah"},
                {"policy", "http://commcent.swc-empire.com/policy"}});

        this.setName(H3lp.NAME);
        this.setLogin(H3lp.NAME);
        try {
            this.connect(H3lp.SERVER);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        for (String c: H3lp.CHANNELS) {
            this.joinChannel(c);
        }
    }

    public void addLink(String[][] strings) {
        for (String[] c : strings) {
            this.addLink(c[0], c[1]);
        }
    }

    public void addLink(String key, String link) {
        this.links.put(key, link);
    }

    @Override
    public void onMessage(String channel, String sender, String login, String hostname, String message) {
       String[] a =  message.trim().split("\\s+");
       if(a.length > 0) {
           String command = a[0];
           if(command.equals("!link")) {
               this.hmLink(a, channel);
           } else if (command.equals("!links")) {
               this.hmLinks(channel);
           } else if (command.equals("!explain")) {
               this.hmExplain(a);
           }
       }
    }

    @Override
    public void onJoin(String channel, String sender, String login, String hostname) {
        this.sendNotice(sender, "Welcome to the GE Help Channel, " + sender +
                ". If you have been banned from the main channel, say '!banned'. Say '!help access' for information regarding joining of our main channel.");
    }


    public void hmLink(String[] message, String channel) {
        if(message.length > 1 && this.links.get(message[1]) != null)
            this.sendMessage(channel, (String) this.links.get(message[1]));
    }

    public void hmLinks(String channel) {
        String args = "";
        for(String c: (Set<String>) this.links.keySet()) {
            args += c + ", ";
        }
        this.sendMessage(channel, "The following links are available: " + args.trim());
    }

    public void hmExplain(String[] message) {
        System.out.print("Called hmExplain with: ");
        for (String s : message) {
            System.out.print(s + " ");
        }
    }
}
