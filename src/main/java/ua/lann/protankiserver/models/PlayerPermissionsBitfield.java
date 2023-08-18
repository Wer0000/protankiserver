package ua.lann.protankiserver.models;

import lombok.Getter;
import ua.lann.protankiserver.enums.PlayerPermissions;

public class PlayerPermissionsBitfield {
    @Getter private final Bitfield bitfield;

    public PlayerPermissionsBitfield(long aLong) {
        this.bitfield = new Bitfield(aLong);
    }

    public PlayerPermissionsBitfield() {
        this.bitfield = new Bitfield();
    }

    public void add(PlayerPermissions permission) {
        bitfield.add(permission.getBit());
    }
    public void remove(PlayerPermissions permission) {
        bitfield.remove(permission.getBit());
    }
    public boolean has(PlayerPermissions permission) {
        return bitfield.has(PlayerPermissions.Root.ordinal()) || bitfield.has(permission.getBit());
    }
}
