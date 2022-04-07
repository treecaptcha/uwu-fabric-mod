package io.github.ran.uwu.client.mixins;

import io.github.ran.uwu.client.UwUMod;
import io.github.ran.uwu.client.Uwuifier;
import io.github.ran.uwu.client.config.UwuConfig;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.MessageType;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = ClientPlayNetworkHandler.class, priority = -2)
public abstract class ClientPlayNetworkHandlerMixin {
    @ModifyVariable(method = "onGameMessage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private GameMessageS2CPacket onGameMessage(GameMessageS2CPacket packet) {
        if (UwuConfig.uwuifyIncoming) {
            if (packet.getType() == MessageType.CHAT) {
                packet = new GameMessageS2CPacket(uwufiedText(packet.getMessage()), packet.getType(), packet.getSender());
            }
        }
        return packet;
    }

    @Unique
    private Text uwufiedText(Text text) {
        return new LiteralText(Uwuifier.uwu(text.getString())).setStyle(text.getStyle());
    }
}
