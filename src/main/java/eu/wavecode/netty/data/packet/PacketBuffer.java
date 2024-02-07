package eu.wavecode.netty.data.packet;

import io.netty5.buffer.Buffer;
import io.netty5.buffer.BufferComponent;
import io.netty5.buffer.ByteCursor;
import io.netty5.buffer.ComponentIterator;
import io.netty5.util.Send;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * #  || Created ||
 * #      By   >> GamingLPyt
 * #      Date >> February 05, 2024
 * #      Time >> 21:57
 * #
 * #  || Project ||
 * #      Name >> WaveNettyPackets
 */
public class PacketBuffer implements Buffer {

    private final Buffer internalBuffer;

    public PacketBuffer(final Buffer internalBuffer) {
        this.internalBuffer = internalBuffer;
    }

    public void writeUUID(final UUID value) {
        if (this.writableBytes() < 16) this.ensureWritable(16);
        this.writeLong(value.getMostSignificantBits());
        this.writeLong(value.getLeastSignificantBits());
    }

    public UUID readUUID() {
        if (this.readableBytes() < 16)
            throw new IndexOutOfBoundsException("Not enough readableBytes to read UUID: " + this.readableBytes() + " / 16");
        return new UUID(this.readLong(), this.readLong());
    }

    public void writeUTF8(String value) {
        if (value == null) value = "";
        final byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        this.writeInt(bytes.length);
        this.writeBytes(bytes);
    }

    public String readUTF8() {
        final int length = this.readInt();
        final byte[] data = new byte[length];
        this.readBytes(data, 0, length);
        return new String(data, StandardCharsets.UTF_8);
    }

    public <T extends PacketEncoder> void writeCollection(final Collection<T> collection) {
        this.writeCollection(collection, PacketEncoder::write);
    }

    public <T extends PacketDecoder> List<T> readCollection(final Supplier<T> factory) {
        return this.readCollection(buffer -> {
            final T instance = factory.get();
            instance.read(this);
            return instance;
        });
    }

    public <T> void writeCollection(final Collection<T> collection, final CallableEncoder<T> encoder) {
        this.writeInt(collection.size());
        for (final T entry : collection) encoder.write(entry, this);
    }

    public <T> List<T> readCollection(final CallableDecoder<T> decoder) {
        final int size = this.readInt();
        final List<T> data = new ArrayList<>(size);
        for (int i = 0; i < size; i++) data.add(decoder.read(this));
        return data;
    }

    public void writeIntCollection(final Collection<Integer> collection) {
        this.writeCollection(collection, (data, buffer) -> buffer.writeInt(data));
    }

    public List<Integer> readIntCollection() {
        return this.readCollection(PacketBuffer::readInt);
    }

    public void writeStringCollection(final Collection<String> collection) {
        this.writeCollection(collection, (data, buffer) -> buffer.writeUTF8(data));
    }

    public List<String> readStringCollection() {
        return this.readCollection(PacketBuffer::readUTF8);
    }

    public void writeUuidCollection(final Collection<UUID> collection) {
        this.writeCollection(collection, (data, buffer) -> buffer.writeUUID(data));
    }

    public List<UUID> readUuidCollection() {
        return this.readCollection(PacketBuffer::readUUID);
    }

    @Override
    public int capacity() {
        return this.internalBuffer.capacity();
    }

    @Override
    public int readerOffset() {
        return this.internalBuffer.readerOffset();
    }

    @Override
    public Buffer readerOffset(final int i) {
        return this.internalBuffer.readerOffset(i);
    }

    @Override
    public int writerOffset() {
        return this.internalBuffer.writerOffset();
    }

    @Override
    public Buffer writerOffset(final int i) {
        return this.internalBuffer.writerOffset(i);
    }

    @Override
    public Buffer fill(final byte b) {
        return this.internalBuffer.fill(b);
    }

    @Override
    public Buffer makeReadOnly() {
        return this.internalBuffer.makeReadOnly();
    }

    @Override
    public boolean readOnly() {
        return this.internalBuffer.readOnly();
    }

    @Override
    public boolean isDirect() {
        return this.internalBuffer.isDirect();
    }

    @Override
    public Buffer implicitCapacityLimit(final int i) {
        return this.internalBuffer.implicitCapacityLimit(i);
    }

    @Override
    public int implicitCapacityLimit() {
        return this.internalBuffer.implicitCapacityLimit();
    }

    @Override
    public void copyInto(final int i, final byte[] bytes, final int i1, final int i2) {
        this.internalBuffer.copyInto(i, bytes, i1, i2);
    }

    @Override
    public void copyInto(final int i, final ByteBuffer byteBuffer, final int i1, final int i2) {
        this.internalBuffer.copyInto(i, byteBuffer, i1, i2);
    }

    @Override
    public void copyInto(final int i, final Buffer buffer, final int i1, final int i2) {
        this.internalBuffer.copyInto(i, buffer, i1, i2);
    }

    @Override
    public int transferTo(final WritableByteChannel writableByteChannel, final int i) throws IOException {
        return this.internalBuffer.transferTo(writableByteChannel, i);
    }

    @Override
    public int transferFrom(final FileChannel fileChannel, final long l, final int i) throws IOException {
        return this.internalBuffer.transferFrom(fileChannel, l, i);
    }

    @Override
    public int transferFrom(final ReadableByteChannel readableByteChannel, final int i) throws IOException {
        return this.internalBuffer.transferFrom(readableByteChannel, i);
    }

    @Override
    public int bytesBefore(final byte b) {
        return this.internalBuffer.bytesBefore(b);
    }

    @Override
    public int bytesBefore(final Buffer buffer) {
        return this.internalBuffer.bytesBefore(buffer);
    }

    @Override
    public ByteCursor openCursor() {
        return this.internalBuffer.openCursor();
    }

    @Override
    public ByteCursor openCursor(final int i, final int i1) {
        return this.internalBuffer.openCursor(i, i1);
    }

    @Override
    public ByteCursor openReverseCursor(final int i, final int i1) {
        return this.internalBuffer.openReverseCursor(i, i1);
    }

    @Override
    public Buffer ensureWritable(final int i, final int i1, final boolean b) {
        return this.internalBuffer.ensureWritable(i, i1, b);
    }

    @Override
    public Buffer copy(final int i, final int i1, final boolean b) {
        return this.internalBuffer.copy(i, i1, b);
    }

    @Override
    public Buffer split(final int i) {
        return this.internalBuffer.split(i);
    }

    @Override
    public Buffer compact() {
        return this.internalBuffer.compact();
    }

    @Override
    public int countComponents() {
        return this.internalBuffer.countComponents();
    }

    @Override
    public int countReadableComponents() {
        return this.internalBuffer.countReadableComponents();
    }

    @Override
    public int countWritableComponents() {
        return this.internalBuffer.countWritableComponents();
    }

    @Override
    public <T extends BufferComponent & ComponentIterator.Next> ComponentIterator<T> forEachComponent() {
        return this.internalBuffer.forEachComponent();
    }

    @Override
    public byte readByte() {
        return this.internalBuffer.readByte();
    }

    @Override
    public byte getByte(final int i) {
        return this.internalBuffer.getByte(i);
    }

    @Override
    public int readUnsignedByte() {
        return this.internalBuffer.readUnsignedByte();
    }

    @Override
    public int getUnsignedByte(final int i) {
        return this.internalBuffer.getUnsignedByte(i);
    }

    @Override
    public Buffer writeByte(final byte b) {
        return this.internalBuffer.writeByte(b);
    }

    @Override
    public Buffer setByte(final int i, final byte b) {
        return this.internalBuffer.setByte(i, b);
    }

    @Override
    public Buffer writeUnsignedByte(final int i) {
        return this.internalBuffer.writeUnsignedByte(i);
    }

    @Override
    public Buffer setUnsignedByte(final int i, final int i1) {
        return this.internalBuffer.setUnsignedByte(i, i1);
    }

    @Override
    public char readChar() {
        return this.internalBuffer.readChar();
    }

    @Override
    public char getChar(final int i) {
        return this.internalBuffer.getChar(i);
    }

    @Override
    public Buffer writeChar(final char c) {
        return this.internalBuffer.writeChar(c);
    }

    @Override
    public Buffer setChar(final int i, final char c) {
        return this.internalBuffer.setChar(i, c);
    }

    @Override
    public short readShort() {
        return this.internalBuffer.readShort();
    }

    @Override
    public short getShort(final int i) {
        return this.internalBuffer.getShort(i);
    }

    @Override
    public int readUnsignedShort() {
        return this.internalBuffer.readUnsignedShort();
    }

    @Override
    public int getUnsignedShort(final int i) {
        return this.internalBuffer.getUnsignedShort(i);
    }

    @Override
    public Buffer writeShort(final short i) {
        return this.internalBuffer.writeShort(i);
    }

    @Override
    public Buffer setShort(final int i, final short i1) {
        return this.internalBuffer.setShort(i, i1);
    }

    @Override
    public Buffer writeUnsignedShort(final int i) {
        return this.internalBuffer.writeUnsignedShort(i);
    }

    @Override
    public Buffer setUnsignedShort(final int i, final int i1) {
        return this.internalBuffer.setUnsignedShort(i, i1);
    }

    @Override
    public int readMedium() {
        return this.internalBuffer.readMedium();
    }

    @Override
    public int getMedium(final int i) {
        return this.internalBuffer.getMedium(i);
    }

    @Override
    public int readUnsignedMedium() {
        return this.internalBuffer.readUnsignedMedium();
    }

    @Override
    public int getUnsignedMedium(final int i) {
        return this.internalBuffer.getUnsignedMedium(i);
    }

    @Override
    public Buffer writeMedium(final int i) {
        return this.internalBuffer.writeMedium(i);
    }

    @Override
    public Buffer setMedium(final int i, final int i1) {
        return this.internalBuffer.setMedium(i, i1);
    }

    @Override
    public Buffer writeUnsignedMedium(final int i) {
        return this.internalBuffer.writeUnsignedMedium(i);
    }

    @Override
    public Buffer setUnsignedMedium(final int i, final int i1) {
        return this.internalBuffer.setUnsignedMedium(i, i1);
    }

    @Override
    public int readInt() {
        return this.internalBuffer.readInt();
    }

    @Override
    public int getInt(final int i) {
        return this.internalBuffer.getInt(i);
    }

    @Override
    public long readUnsignedInt() {
        return this.internalBuffer.readUnsignedInt();
    }

    @Override
    public long getUnsignedInt(final int i) {
        return this.internalBuffer.getUnsignedInt(i);
    }

    @Override
    public Buffer writeInt(final int i) {
        return this.internalBuffer.writeInt(i);
    }

    @Override
    public Buffer setInt(final int i, final int i1) {
        return this.internalBuffer.setInt(i, i1);
    }

    @Override
    public Buffer writeUnsignedInt(final long l) {
        return this.internalBuffer.writeUnsignedInt(l);
    }

    @Override
    public Buffer setUnsignedInt(final int i, final long l) {
        return this.internalBuffer.setUnsignedInt(i, l);
    }

    @Override
    public float readFloat() {
        return this.internalBuffer.readFloat();
    }

    @Override
    public float getFloat(final int i) {
        return this.internalBuffer.getFloat(i);
    }

    @Override
    public Buffer writeFloat(final float v) {
        return this.internalBuffer.writeFloat(v);
    }

    @Override
    public Buffer setFloat(final int i, final float v) {
        return this.internalBuffer.setFloat(i, v);
    }

    @Override
    public long readLong() {
        return this.internalBuffer.readLong();
    }

    @Override
    public long getLong(final int i) {
        return this.internalBuffer.getLong(i);
    }

    @Override
    public Buffer writeLong(final long l) {
        return this.internalBuffer.writeLong(l);
    }

    @Override
    public Buffer setLong(final int i, final long l) {
        return this.internalBuffer.setLong(i, l);
    }

    @Override
    public double readDouble() {
        return this.internalBuffer.readDouble();
    }

    @Override
    public double getDouble(final int i) {
        return this.internalBuffer.getDouble(i);
    }

    @Override
    public Buffer writeDouble(final double v) {
        return this.internalBuffer.writeDouble(v);
    }

    @Override
    public Buffer setDouble(final int i, final double v) {
        return this.internalBuffer.setDouble(i, v);
    }

    @Override
    public Send<Buffer> send() {
        return this.internalBuffer.send();
    }

    @Override
    public void close() {
        this.internalBuffer.close();
    }

    @Override
    public boolean isAccessible() {
        return this.internalBuffer.isAccessible();
    }
}
