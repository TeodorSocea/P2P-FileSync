package Networking.Messages;

import java.nio.ByteBuffer;

/**
 * The Messages class contains various useful constants for messages
 *
 * General message format (everything between "[]" is an integer - 4 bytes):
 *
 * [MSG_SIZE][SWARM_ID][USER_ID][MSG_TYPE][[DATA]]
 *
 * For info on the contents of [[DATA]] look at the comments on the MessageHeader enum
 */

public class Messages {
    public static final int MAX_SIZE = 1024;
    public static final int UNAUTHENTICATED = 0;
    public static final int NO_SWARM = 0;
    public static final byte[] KEY = {108,109,97,111,32,103,97,121};

    public static byte[] toByteArray(int in){
        ByteBuffer buf = ByteBuffer.allocate(4);
        buf.putInt(4);
        return buf.array();
    }
    
    public static byte[] decryptSize(byte[] size){
        for (int i = 0; i < size.length; i++) {
            size[i] = (byte) (size[i] ^ Messages.KEY[i % Messages.KEY.length]);
        }
        return size;
    }

    public static int getIntFromByteArray(byte[] input, int offset){
        return  ((input[offset    ] & 0xFF) << 24) |
                ((input[offset + 1] & 0xFF) << 16) |
                ((input[offset + 2] & 0xFF) << 8 ) |
                ((input[offset + 3] & 0xFF));
    }
    public static byte getByteFromByteArray(byte[] input, int offset){
        return input[offset];
    }


}
