package Networking.Messages;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptedMessage extends Message{

    private static String password = "Password123";

    private static byte[] key = { 1, 2 };

//    static {
//        try {
//            key = MessageDigest.getInstance("SHA-256").digest(password.getBytes(StandardCharsets.UTF_8));
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }


    public EncryptedMessage(byte[] rawMessage) {
        super(rawMessage);
    }

    public static void setKey(byte[] newKey){
        if(newKey != null)
            key = newKey;
    }

    public static EncryptedMessage encrypt(Message message){
        byte[] msg = message.toPacket();

        for (int i = 0; i < msg.length; i++) {
            msg[i] = (byte) (msg[i] ^ key[i % key.length]);
        }

        return new EncryptedMessage(msg);
    }

    public static Message decrypt(EncryptedMessage message){
        byte[] msg = message.toPacket();

        for (int i = 0; i < msg.length; i++) {
            msg[i] = (byte) (msg[i] ^ key[i % key.length]);
        }

        return new Message(msg);
    }

    public byte[] toPacket(){
        return rawMessage;
    }

}
