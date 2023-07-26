package ua.lann.protankiserver.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.Random;

public class Encryption {
    private int decryptPosition;
    private int encryptPosition;
    private final int encryptionLength = 8;
    private final int[] decryptKeys = new int[encryptionLength];
    private final int[] encryptKeys = new int[encryptionLength];

    public Encryption() {
        generateKeys();
    }

    private int[] generateKeys() {
        Random random = new Random();
        int[] keys = new int[] { -71, -89, -97, -102 };
        for (int i = 0; i < 4; i++) {
            int key = keys[i];
            decryptKeys[i] = key;
            encryptKeys[i] = key;
        }

        return keys;
    }

    public ByteBuf encryptionPacket() {
        int[] keys = generateKeys();

        ByteBuf packet = Unpooled.buffer();
        packet.writeInt(keys.length);

        for (int key : keys) {
            packet.writeByte((byte) key);
        }

        setCrypsKeys(keys);

        return packet;
    }

    private void setCrypsKeys(int[] keys) {
        int base = 0;
        for (int key : keys) {
            base ^= key;
        }

        for (int i = 0; i < encryptionLength; i++) {
            int encryptionKey = base ^ (i << 3);
            int decryptionKey = encryptionKey ^ 87;

            encryptKeys[i] = encryptionKey;
            decryptKeys[i] = decryptionKey;
        }
    }

    public ByteBuf decrypt(ByteBuf packet) {
        ByteBuf decryptedPacket = packet.copy();

        for (int i = 0; i < decryptedPacket.readableBytes(); i++) {
            byte byteToDecrypt = decryptedPacket.getByte(i);
            decryptKeys[decryptPosition] ^= byteToDecrypt;
            decryptedPacket.setByte(i, (byte) decryptKeys[decryptPosition]);
            decryptPosition ^= decryptKeys[decryptPosition] & 7;
        }

        return decryptedPacket;
    }

    public ByteBuf encrypt(ByteBuf packet) {
        ByteBuf encryptedPacket = Unpooled.buffer();

        for (int i = 0; i < packet.readableBytes(); i++) {
            short byteToEncrypt = packet.getUnsignedByte(i);
            encryptedPacket.writeByte((byte) (byteToEncrypt ^ encryptKeys[encryptPosition]));
            encryptKeys[encryptPosition] = byteToEncrypt;
            encryptPosition ^= byteToEncrypt & 7;
        }

        return encryptedPacket;
    }
}