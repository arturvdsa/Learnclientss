package com.example.learnclient.mixin;

import com.example.learnclient.modules.XRayModule;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** X-Ray: при включённом модуле рисуем грани только у «интересных» блоков. */
@Mixin(Block.class)
public class BlockMixin {
    @Inject(
        method = "shouldDrawSide(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z",
        at = @At("HEAD"), cancellable = true, require = 0)
    private static void learnclient$xray(BlockState state, BlockState other, Direction side, CallbackInfoReturnable<Boolean> cir) {
        if (XRayModule.active) cir.setReturnValue(XRayModule.isVisible(state.getBlock()));
    }
}
