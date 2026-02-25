# Registry Event code example

```java
InfinityEventBus.register(Event_ID.class, event -> {
    System.out.println("MOD-B received Event");
});
```

# Run Event code example

```java
InfinityEventBus.post(new Event_ID());
```