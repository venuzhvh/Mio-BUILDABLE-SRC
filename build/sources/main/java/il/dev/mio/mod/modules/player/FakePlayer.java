//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\Primo\Downloads\Deobf\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

// 
// Decompiled by Procyon v0.5.36
// 

package il.dev.mio.mod.modules.player;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraft.world.GameType;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import il.dev.mio.api.util.IMinecraftUtil;
import il.dev.mio.mod.command.Command;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import il.dev.mio.mod.gui.clickgui.setting.Setting;
import il.dev.mio.mod.modules.Module;

public class FakePlayer extends Module
{
    public Setting<String> fakename;
    private EntityOtherPlayerMP clonedPlayer;
    
    public FakePlayer() {
        super("FakePlayer", "Summons a client side fake player", Category.PLAYER, false, false, false);
        this.fakename = (Setting<String>)this.register(new Setting("Name", "Herobrine"));
    }
    
    @Override
    public String getDisplayInfo() {
        return this.fakename.getValue();
    }
    
    @Override
    public void onEnable() {
        Command.sendMessage("Spawned a fakeplayer with the name " + this.fakename.getValueAsString() + ".");
        if (IMinecraftUtil.Util.mc.player == null || IMinecraftUtil.Util.mc.player.isDead) {
            this.disable();
            return;
        }
        (this.clonedPlayer = new EntityOtherPlayerMP((World)IMinecraftUtil.Util.mc.world, new GameProfile(UUID.fromString("0f75a81d-70e5-43c5-b892-f33c524284f2"), this.fakename.getValueAsString()))).copyLocationAndAnglesFrom((Entity)IMinecraftUtil.Util.mc.player);
        this.clonedPlayer.rotationYawHead = IMinecraftUtil.Util.mc.player.rotationYawHead;
        this.clonedPlayer.rotationYaw = IMinecraftUtil.Util.mc.player.rotationYaw;
        this.clonedPlayer.rotationPitch = IMinecraftUtil.Util.mc.player.rotationPitch;
        this.clonedPlayer.setGameType(GameType.SURVIVAL);
        this.clonedPlayer.inventory.copyInventory(FakePlayer.mc.player.inventory);
        this.clonedPlayer.setHealth(20.0f);
        IMinecraftUtil.Util.mc.world.addEntityToWorld(-12345, (Entity)this.clonedPlayer);
        this.clonedPlayer.onLivingUpdate();
    }
    
    @Override
    public void onDisable() {
        if (IMinecraftUtil.Util.mc.world != null) {
            IMinecraftUtil.Util.mc.world.removeEntityFromWorld(-12345);
        }
    }
    
    @SubscribeEvent
    public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        if (this.isEnabled()) {
            this.disable();
        }
    }
}
