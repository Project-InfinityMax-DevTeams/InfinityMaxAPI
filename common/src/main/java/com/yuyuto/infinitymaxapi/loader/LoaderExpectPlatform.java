package com.yuyuto.infinitymaxapi.loader;

public interface LoaderExpectPlatform {

    String id(String path);
    boolean isClient();

    Registries registries();
    Network network();
    Events events();

    interface Registries {
        <T> void item(String name, T item);
        <T> void block(String name, T block, float strength, boolean noOcclusion);
        <T, C> void entity(String name, T entityType, C category, float width, float height);
        <T, B> void blockEntity(String name, T blockEntityType, B... blocks);

        /** DataGen登録。 */
        default <T> void dataGen(String name, T dataGenDefinition) {
            throw new UnsupportedOperationException("dataGen is not implemented on this platform");
        }

        /** GUI登録。 */
        default <T> void gui(String name, T guiDefinition) {
            throw new UnsupportedOperationException("gui is not implemented on this platform");
        }

        /** ワールド要素（ディメンション/バイオーム/構造物）登録。 */
        default <T> void world(String name, T worldDefinition) {
            throw new UnsupportedOperationException("dataGen is not implemented on this platform");
        }

        /** ネットワーク登録。 */
        default <T> void network(String name, T networkDefinition) {
                throw new UnsupportedOperationException("network is not implemented on this platform");
        }

        /** パケット登録。 */
        default <T> void packet(String name, T packetDefinition) {
            throw new UnsupportedOperationException("packet is not implemented on this platform");
        }
    }

    interface Network {
        void register();
        <T> void sendToServer(T packet);
        <T> void sendToPlayer(T player, T packet);
    }

    interface Events {
        void register();
        <T> void onPlayerJoin(T player);
    }
}
