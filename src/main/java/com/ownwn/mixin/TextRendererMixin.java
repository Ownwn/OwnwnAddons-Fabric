package com.ownwn.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.CharacterVisitor;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {



//    @ModifyVariable(at = @At("HEAD"), method = "drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I", argsOnly = true)
//    public Text draw(Text text) {
////        if (MinecraftClient.getInstance().player == null) {
////            return text;
////        }
//////        System.out.println("text 1: " + text.getString());
////        return text.copy().append("|");
//        Style textStyle = text.getStyle();
//        String textString = text.getString().replace("o", "b");
//        System.out.println(text);
//        return Text.empty().append(textString).setStyle(textStyle);
//    }

//    @ModifyVariable(at = @At("HEAD"), method = "drawWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/OrderedText;FFI)I", argsOnly = true)
//    public OrderedText draw2(OrderedText text) {
//
//        System.out.println("text 2: " + text.toString());
//        String newText = text + "|";
//        return text;
//    }

//    @ModifyVariable(at = @At("HEAD"), method = "drawInternal(Lnet/minecraft/text/OrderedText;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", argsOnly = true)
//    public int draw3(OrderedText text) {
//
////        System.out.println("text 2: " + text.toString());
////        String newText = text + "|";
//        text.toString();
//
//        return text;
//    }

//    @Inject(method = "drawInternal(Ljava/lang/String;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;IIZ)I", at = @At("HEAD"), cancellable = true)
//    private void modifyColor(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> ci) {
//
//        color = 0xFFFF0000;
//
//        // Call the original method with the modified color
////        int result = ((TextRenderer)(Object)this).drawInternal(text, x, y, color, shadow, matrix, vertexConsumerProvider, layerType, backgroundColor, light);
////        int result = MinecraftClient.getInstance().textRenderer.draw(text, x, y, color, shadow, matrix, vertexConsumerProvider, layerType, backgroundColor, light);
//        MinecraftClient.getInstance().textRenderer.draw("test", x, y, color, shadow, matrix, vertexConsumerProvider, layerType, backgroundColor, light);
//
//
//        // Return the result
//        ci.cancel();
////        ci.setReturnValue(result);
//    }



//    @Inject(method = "draw", at = @At("HEAD"), cancellable = true)
//    private void draw(MatrixStack matrix, String text, float x, float y, int color, boolean shadow, CallbackInfoReturnable<Integer> cir) {
//        // Replace with your logic to modify 'text'
//        text = text.replace("old text", "new text");
//
//        // Do not call the draw method here to prevent recursive calls, we will continue with the original method
//    }

//    @Inject(method = "drawInternal(Lnet/minecraft/text/OrderedText;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/font/TextRenderer$TextLayerType;II)I", at = @At("HEAD"), cancellable = true)
//    private void beforeDrawInternal(OrderedText text, float x, float y, int color, boolean shadow, Matrix4f matrix, VertexConsumerProvider vertexConsumerProvider, TextRenderer.TextLayerType layerType, int backgroundColor, int light, CallbackInfoReturnable<Integer> cir) {
//        color = 0;
//        Matrix4f matrix4f = new Matrix4f(matrix);
//        if (shadow) {
////            MinecraftClient.getInstance().textRenderer.draw(text, x, y, color, true, matrix4f, vertexConsumerProvider, layerType, backgroundColor, light);
//            matrix4f.translate(new Vector3f(0.0f, 0.0f, 0.03f));
//        }
//        x = MinecraftClient.getInstance().textRenderer.draw(text, x, y, color, false, matrix4f, vertexConsumerProvider, layerType, backgroundColor, light);
//
//        cir.setReturnValue((int)x + (shadow ? 1 : 0));
////        cir.cancel();
//    }

    @Inject(method = "draw(Lnet/minecraft/text/OrderedText;FFILorg/joml/Matrix4f;Z)I", at = @At("HEAD"), cancellable = true)
    private void beforeDrawInternal(OrderedText text, float x, float y, int color, Matrix4f matrix, boolean shadow, CallbackInfoReturnable<Integer> cir) {
//        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
//        int i = MinecraftClient.getInstance().textRenderer.draw(text, x, y, 1, shadow, matrix, (VertexConsumerProvider)immediate, TextRenderer.TextLayerType.NORMAL, 0, 0xF000F0);
//        immediate.draw();
//
//        cir.setReturnValue(i);
//        ChatHudLine.Visible visible;
//        visible.
//        cir.setReturnValue(MinecraftClient.getInstance().textRenderer.draw(text, x, y, color, matrices.peek().getPositionMatrix(), true));
    }


}
