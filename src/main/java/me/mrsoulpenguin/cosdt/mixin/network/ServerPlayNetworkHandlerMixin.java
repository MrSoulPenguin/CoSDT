package me.mrsoulpenguin.cosdt.mixin.network;

import me.mrsoulpenguin.cosdt.callback.MessageSendCallback;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public abstract ServerPlayerEntity getPlayer();

    @Inject(method = "onChatMessage", at = @At("TAIL"))
    private void invokeMessageSendCallback(ChatMessageC2SPacket packet, CallbackInfo ci) {
        MessageSendCallback.EVENT.invoker().chat(this.getPlayer(), packet.chatMessage());
    }

}
