package com.ownwn.mixin;

import com.ownwn.config.Config;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;ZDDDDDD)V", at = @At("HEAD"), cancellable = true)
    public void addParticle(ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfo ci) {
        if (!Config.get().hideExplosionParticles) {
            return;
        }
        if (!parameters.asString().equals("minecraft:explosion")) {
            return;
        }
        // TODO: make this work with just hyperion explosions, not all explosions
        ci.cancel();
    }
}
