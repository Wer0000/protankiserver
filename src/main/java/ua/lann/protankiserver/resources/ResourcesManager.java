package ua.lann.protankiserver.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.protocol.packets.PacketId;
import ua.lann.protankiserver.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.util.JsonUtils;

import java.util.Arrays;
import java.util.HashMap;

public class ResourcesManager {
    private static final Logger logger = LoggerFactory.getLogger(ResourcesManager.class);

    private static final HashMap<Integer, ResourceInfo> staticResources = new HashMap<>();
    static {
        JsonObject object = JsonUtils.readJsonObject("resources.json");
        for(String idlow : object.keySet()) {
            JsonObject res = object.getAsJsonObject(idlow);
            staticResources.put(Integer.valueOf(idlow), new ResourceInfo(res));
        }
    }

    private final HashMap<Integer, ResourceLoadedCallback> callbacks;
    private final HashMap<ResourcesPack, Integer[]> resources;

    private final ClientController controller;

    public ResourcesManager(ClientController controller) {
        callbacks = new HashMap<>();
        this.controller = controller;

        resources = new HashMap<>();
        resources.put(ResourcesPack.Main, loadResources("main.json"));
    }

    public static ResourceInfo getResource(Integer idlow) {
        return staticResources.get(idlow);
    }

    private Integer[] loadResources(String resourceName) {
        JsonArray array = JsonUtils.readJsonArray(resourceName);

        if(array == null) {
            logger.error("Missing resource file {}", resourceName);
            return new Integer[0];
        }

        int[] intArray = new int[array.size()];
        for (int i = 0; i < array.size(); i++) intArray[i] = array.get(i).getAsInt();

        return Arrays.stream(intArray)
            .boxed()
            .toArray(Integer[]::new);
    }

    public void notifyLoaded(int callbackId) {
        if(!this.callbacks.containsKey(callbackId)) return;

        logger.info("Resource loaded: {}", ResourcesPack.getByCallbackId(callbackId));
        this.callbacks.get(callbackId).onLoaded();
    }

    public void loadSingle(int resource) {
        ByteBuf buf = Unpooled.buffer();

        buf.writeInt(resource);
        controller.sendPacket(PacketId.LoadSingleResource, buf);

        buf.release();
    }

    public void load(ResourcesPack pack, ResourceLoadedCallback callback) {
        load(resources.get(pack), callback, pack.callbackId);
    }

    public void load(Integer[] resources, ResourceLoadedCallback callback, int callbackId) {
        ByteBuf buffer = Unpooled.buffer();
        JsonObject object = new JsonObject();
        JsonArray array = new JsonArray();

        for(int i : resources) {
            ResourceInfo info = getResource(i);
            array.add(info.toJsonObject());
        }

        object.add("resources", array);

        ICodec<JsonObject> objectICodec = CodecRegistry.getCodec(JsonObject.class);
        objectICodec.encode(buffer, object);
        buffer.writeInt(callbackId);

        this.callbacks.put(callbackId, callback);
        controller.sendPacket(PacketId.LoadResources, buffer);
    }
}
