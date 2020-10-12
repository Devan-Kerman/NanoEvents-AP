package net.fabricmc.example;

import net.devtech.nanoevents.annotations.Listener;

public class Listeners {
    public @interface Test {
        Class<?>[] value();
    }

    @Listener(value = "examplemod:init", args = Test.class)
    @Test({Listener.class, Listeners.class})
    public static void init(Listeners testParam) {
        System.out.println("examplemod init event!");
    }

    @Listener("examplemod:post_init")
    public static Listeners postInit() {
        System.out.println("Example mod post init event!");
        return null;
    }
}
