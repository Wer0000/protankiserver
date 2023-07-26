package ua.lann.protankiserver.resources;

@FunctionalInterface
public interface ResourceLoadedCallback {
    void onLoaded(int callbackId);
}
