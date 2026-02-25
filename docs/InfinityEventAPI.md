# Registry Event code example

```java
InfinityEventBus.register(EVENT_ID.class, event -> {
    if (isCanelFlagMethod("reason method here(your method)")) {
        event.cancel();
    }
});
```

# Run Event code example

```java
EVENT_ID event = InfinityEventBus.post(new EVENT_ID(...));

if (!event.isCancelled()) {
    event();
}
```