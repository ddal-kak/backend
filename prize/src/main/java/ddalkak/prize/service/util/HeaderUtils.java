package ddalkak.prize.service.util;

import java.nio.ByteBuffer;

public class HeaderUtils {
    public static int toInteger(byte[] origin) {
        if(origin == null) {
            return 1;
        }
        return ByteBuffer.wrap(origin).getInt();
    }

    public static byte[] toByteArray(int origin) {
        return ByteBuffer.allocate(4).putInt(origin).array();
    }
}
