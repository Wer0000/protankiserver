package ua.lann.protankiserver.models;

import lombok.Getter;
import lombok.Setter;

public class Bitfield {
    @Getter @Setter
    private long bitfield;

    public Bitfield() {}
    public Bitfield(long aLong) { bitfield = aLong; }

    public void add(int bit) {
        bitfield |= 1L << bit;
    }
    public void remove(int bit) {
        bitfield &= ~(1L << bit);
    }
    public boolean has(int bit) {
        long pow = (1L << bit);
        return (bitfield & pow) == pow;
    }
}
