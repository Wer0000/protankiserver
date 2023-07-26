package ua.lann.protankiserver.resources;

import com.google.gson.JsonObject;

public class ResourceInfo {
    int idhigh = 0;
    int idlow = 0;
    int versionhigh = 0;
    int versionlow = 0;
    int type = 0;
    boolean lazy = false;
    boolean alpha = false;

    public ResourceInfo(JsonObject obj) {
        idhigh = obj.get("idhigh").getAsInt();
        idlow = obj.get("idlow").getAsInt();
        versionhigh = obj.get("versionhigh").getAsInt();
        versionlow = obj.get("versionlow").getAsInt();
        type = obj.get("type").getAsInt();
        if(obj.has("lazy")) lazy = obj.get("lazy").getAsBoolean();
        if(obj.has("alpha")) alpha = obj.get("alpha").getAsBoolean();
    }

    public JsonObject toJsonObject() {
        JsonObject object = new JsonObject();
        object.addProperty("idhigh", idhigh);
        object.addProperty("idlow", idlow);
        object.addProperty("versionhigh", versionhigh);
        object.addProperty("versionlow", versionlow);
        object.addProperty("type", type);
        object.addProperty("lazy", lazy);
        object.addProperty("alpha", alpha);

        return object;
    }
}
