package net.fabricmc.example;

import net.devtech.nanoevents.annotations.Listener;

public class Listeners {
    @Listener("examplemod:init")
    public static void init() {
        System.out.println("examplemod init event!");
    }

    @Listener("examplemod:post_init")
    public static void postInit() {
        System.out.println("Example mod post init event!");
    }
}
