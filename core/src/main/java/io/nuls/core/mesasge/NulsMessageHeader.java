package io.nuls.core.mesasge;

import io.nuls.core.chain.entity.BaseNulsData;
import io.nuls.core.crypto.UnsafeByteArrayOutputStream;
import io.nuls.core.exception.NulsException;
import io.nuls.core.utils.crypto.Hex;
import io.nuls.core.utils.crypto.Utils;
import io.nuls.core.utils.io.NulsByteBuffer;
import io.nuls.core.utils.io.NulsOutputStreamBuffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author vivi
 * @date 2017-11-10
 */
public class NulsMessageHeader extends BaseNulsData{

    public static final int MESSAGE_HEADER_SIZE = 20;
    public static final int MESSAGE_HEADER_EXTEND_SIZE = 9;

    private int magicNumber;

    // the NulsMessage length
    private int length;

    //0x01 : networkMessage  //0x02 : eventMessage;
    private short headType;
    public static final short NETWORK_MESSAGE = 1;
    public static final short EVENT_MESSAGE = 2;

    private byte xor;

    private byte[] extend;

    public NulsMessageHeader() {
        this.magicNumber = 0;
        this.length = 0;
        this.headType = 0;
        this.xor = Hex.decode("00")[0];
        this.extend = new byte[MESSAGE_HEADER_EXTEND_SIZE];
    }

    public NulsMessageHeader(int magicNumber, short headType) {
        this();
        this.magicNumber = magicNumber;
        this.headType = headType;
    }

    public NulsMessageHeader(int magicNumber, short headType, int length) {
        this();
        this.magicNumber = magicNumber;
        this.headType = headType;
        this.length = length;
    }

    public NulsMessageHeader(int magicNumber, short headType, byte[] extend) {
        this(magicNumber, headType);
        this.extend = extend;
    }

    public NulsMessageHeader(int magicNumber, short headType, int length, byte xor) {
        this(magicNumber, headType);
        this.length = length;
        this.xor = xor;
    }

    public NulsMessageHeader(int magicNumber, short headType, int length, byte xor, byte[] extend) {
        this(magicNumber, headType, length, xor);
        this.extend = extend;
    }

    public NulsMessageHeader(NulsByteBuffer buffer) throws NulsException {
        parse(buffer);
    }

    @Override
    public int size() {
        return MESSAGE_HEADER_SIZE;
    }

    @Override
    public void serializeToStream(NulsOutputStreamBuffer stream) throws IOException {
        byte[] header = new byte[MESSAGE_HEADER_SIZE];
        Utils.int32ToByteArrayLE(magicNumber, header, 0);
        Utils.int32ToByteArrayLE(length, header, 4);
        Utils.uint16ToByteArrayLE(headType, header, 8);
        header[10] = xor;
        System.arraycopy(extend, 0, header, 11, MESSAGE_HEADER_EXTEND_SIZE);
        stream.write(header);
    }

    @Override
    public void parse(NulsByteBuffer byteBuffer) throws NulsException {
        this.magicNumber = byteBuffer.readInt32LE();
        this.length = byteBuffer.readInt32LE();
        this.headType = byteBuffer.readShort();
        this.xor = byteBuffer.readByte();
        this.extend = byteBuffer.readBytes(MESSAGE_HEADER_EXTEND_SIZE);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("messageHeader:{");
        buffer.append("magicNumber:" + magicNumber + ", ");
        buffer.append("headerType:" + headType + ", ");
        buffer.append("length:" + length + ", ");
        buffer.append("xor:" + xor + ", ");
        buffer.append("extend:[");
        if (extend != null && extend.length > 0) {
            for (byte b : extend) {
                buffer.append(b + " ");
            }
        }
        buffer.append("]}");
        return buffer.toString();
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getExtend() {
        return extend;
    }

    public void setExtend(byte[] extend) {
        this.extend = extend;
    }

    public int getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(int magicNumber) {
        this.magicNumber = magicNumber;
    }

    public short getHeadType() {
        return headType;
    }

    public void setHeadType(short headType) {
        this.headType = headType;
    }

    public byte getXor() {
        return xor;
    }

    public void setXor(byte xor) {
        this.xor = xor;
    }

}
