package ua.lann.protankiserver.game.resources;

import com.squareup.moshi.Json;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ResourceInfo {
    @Json int idhigh;
    @Json int idlow;
    @Json int versionhigh;
    @Json int versionlow;
    @Json int type;
    @Json boolean lazy = false;
    @Json boolean alpha = false;
}
