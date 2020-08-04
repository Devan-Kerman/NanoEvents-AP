package net.fabricmc.example;

import net.devtech.nanoevents.api.Logic;
import net.devtech.nanoevents.api.annotations.Invoker;

@SuppressWarnings("InfiniteRecursion")
public class Invokers {
	@Invoker("examplemod:init")
	public static void init() {
		Logic.start();
		init();
		Logic.end();
	}

	@Invoker("examplemod:pre_init")
	@Invoker.Default("net.fabricmc.example.mixin.NoApplyMixin")
	public static void preInit() {
		// no one will use, so ParameterTestMixin mixin never fires
		Logic.start();
		preInit();
		Logic.end();
	}

	@Invoker("examplemod:post_init")
	@Invoker.Default("net.fabricmc.example.mixin.ApplyMixin")
	public static void postInit() {
		// no one will use, so ParameterTestMixin mixin never fires
		Logic.start();
		postInit();
		Logic.end();
	}
}
