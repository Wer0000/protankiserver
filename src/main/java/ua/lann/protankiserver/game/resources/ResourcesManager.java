package ua.lann.protankiserver.game.resources;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Types;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.lann.protankiserver.ClientController;
import ua.lann.protankiserver.game.protocol.packets.CodecRegistry;
import ua.lann.protankiserver.game.protocol.packets.PacketId;
import ua.lann.protankiserver.game.protocol.packets.codec.ICodec;
import ua.lann.protankiserver.serialization.JsonUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ResourcesManager {
    private static final Logger logger = LoggerFactory.getLogger(ResourcesManager.class);

    private static final HashMap<Integer, ResourceInfo> staticResources = new HashMap<>();
    static {
        Map<String, ResourceInfo> map = JsonUtils.readResource("resources.json", Types.newParameterizedType(
            Map.class,
            String.class,
            ResourceInfo.class
        ));

        for(String idlow : map.keySet()) {
            staticResources.put(Integer.valueOf(idlow), map.get(idlow));
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
        resources.put(ResourcesPack.Garage, loadResources("garage.json"));
    }

    public static ResourceInfo getResource(Integer idlow) {
        return staticResources.get(idlow);
    }

    private Integer[] loadResources(String resourceName) {
        return JsonUtils.readResource(resourceName, Integer[].class);
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

        LoadResourcesModel model = new LoadResourcesModel();
        model.resources = Arrays.stream(resources)
            .map(ResourcesManager::getResource)
            .toArray(ResourceInfo[]::new);

        ICodec<String> stringICodec = CodecRegistry.getCodec(String.class);
        stringICodec.encode(buffer, JsonUtils.toString(model, LoadResourcesModel.class));

        buffer.writeInt(callbackId);
        this.callbacks.put(callbackId, callback);

        controller.sendPacket(PacketId.LoadResources, buffer);
    }
}
