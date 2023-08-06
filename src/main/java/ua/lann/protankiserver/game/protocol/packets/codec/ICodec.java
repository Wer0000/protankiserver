package ua.lann.protankiserver.game.protocol.packets.codec;

import io.netty.buffer.ByteBuf;

public interface ICodec<T> {
    void encode(ByteBuf buf, T data);
    T decode(ByteBuf data);
}
