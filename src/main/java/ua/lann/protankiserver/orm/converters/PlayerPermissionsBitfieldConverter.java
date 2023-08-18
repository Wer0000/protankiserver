package ua.lann.protankiserver.orm.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ua.lann.protankiserver.models.PlayerPermissionsBitfield;

@Converter
public class PlayerPermissionsBitfieldConverter implements AttributeConverter<PlayerPermissionsBitfield, Long> {
    @Override
    public Long convertToDatabaseColumn(PlayerPermissionsBitfield playerPermissionsBitfield) {
        return playerPermissionsBitfield.getBitfield().getBitfield();
    }

    @Override
    public PlayerPermissionsBitfield convertToEntityAttribute(Long aLong) {
        return new PlayerPermissionsBitfield(aLong);
    }
}
