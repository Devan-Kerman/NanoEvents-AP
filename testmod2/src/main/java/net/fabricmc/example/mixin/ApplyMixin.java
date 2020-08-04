package net.fabricmc.example.mixin;

import net.fabricmc.example.ExampleMod;
import net.fabricmc.example.Invokers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ExampleMod.class, remap = false)
public class ApplyMixin {
	@Inject(method = "onInitialize", at = @At("RETURN"))
	private void preInitEvent(CallbackInfo ci) {
		System.out.println("Post Init Event Fire!");
		Invokers.postInit();
	}
}
