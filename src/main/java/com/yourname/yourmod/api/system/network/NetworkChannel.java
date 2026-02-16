public interface NetworkChannel {

    <T> void register(MessageSpec<T> spec);

    <T> void sendToServer(MessageSpec<T> spec, T payload);

    <T> void sendToPlayer(PlayerRef player, MessageSpec<T> spec, T payload);
}