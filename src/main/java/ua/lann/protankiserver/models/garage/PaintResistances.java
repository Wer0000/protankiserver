package ua.lann.protankiserver.models.garage;

import com.squareup.moshi.Json;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ua.lann.protankiserver.orm.entities.garage.ResistancesEntity;
import ua.lann.protankiserver.orm.models.GarageItemProperty;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@MappedSuperclass
public class PaintResistances {
    @Column @Json(name = "ALL_RESISTANCE") int allResistance;
    @Column @Json(name = "SMOKY_RESISTANCE") int smoky;
    @Column @Json(name = "FIREBIRD_RESISTANCE") int firebird;
    @Column @Json(name = "TWINS_RESISTANCE") int twins;
    @Column @Json(name = "RAILGUN_RESISTANCE") int railgun;
    @Column @Json(name = "FREEZE_RESISTANCE") int freeze;
    @Column @Json(name = "ISIS_RESISTANCE") int isis;
    @Column @Json(name = "RICOCHET_RESISTANCE") int ricochet;
    @Column @Json(name = "SHAFT_RESISTANCE") int shaft;
    @Column @Json(name = "SHOTGUN_RESISTANCE") int shotgun;
    @Column @Json(name = "THUNDER_RESISTANCE") int thunder;
    @Column @Json(name = "MINE_RESISTANCE") int mine;

    public PaintResistances(ResistancesEntity resistancesEntity) {
        this.smoky = resistancesEntity.getSmoky();
        this.firebird = resistancesEntity.getFirebird();
        this.twins = resistancesEntity.getTwins();
        this.railgun = resistancesEntity.getRailgun();
        this.freeze = resistancesEntity.getFreeze();
        this.isis = resistancesEntity.getIsis();
        this.ricochet = resistancesEntity.getRicochet();
        this.shaft = resistancesEntity.getShaft();
        this.shotgun = resistancesEntity.getShotgun();
        this.thunder = resistancesEntity.getThunder();
        this.mine = resistancesEntity.getMine();
        this.allResistance = resistancesEntity.getAllResistance();
    }

    public List<GarageItemProperty> toProps() {
        List<GarageItemProperty> props = new ArrayList<>();

        if(smoky != 0) props.add(new GarageItemProperty("SMOKY_RESISTANCE", smoky, null));
        if(firebird != 0) props.add(new GarageItemProperty("FIREBIRD_RESISTANCE", firebird, null));
        if(twins != 0) props.add(new GarageItemProperty("TWINS_RESISTANCE", twins, null));
        if(railgun != 0) props.add(new GarageItemProperty("RAILGUN_RESISTANCE", railgun, null));
        if(freeze != 0) props.add(new GarageItemProperty("FREEZE_RESISTANCE", freeze, null));
        if(isis != 0) props.add(new GarageItemProperty("ISIS_RESISTANCE", isis, null));
        if(ricochet != 0) props.add(new GarageItemProperty("RICOCHET_RESISTANCE", ricochet, null));
        if(shaft != 0) props.add(new GarageItemProperty("SHAFT_RESISTANCE", shaft, null));
        if(shotgun != 0) props.add(new GarageItemProperty("SHOTGUN_RESISTANCE", shotgun, null));
        if(thunder != 0) props.add(new GarageItemProperty("THUNDER_RESISTANCE", thunder, null));
        if(mine != 0) props.add(new GarageItemProperty("MINE_RESISTANCE", mine, null));
        if(allResistance != 0) props.add(new GarageItemProperty("ALL_RESISTANCE", allResistance, null));

        return props;
    }
}
