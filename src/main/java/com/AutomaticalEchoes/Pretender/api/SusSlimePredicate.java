package com.AutomaticalEchoes.Pretender.api;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SusSlimePredicate implements EntitySubPredicate {
    public static final EntitySubPredicate.Type SUS_SLIME = SusSlimePredicate::fromJson;
    private final MinMaxBounds.Ints size;
    public static SusSlimePredicate sized(MinMaxBounds.Ints p_223427_) {
        return new SusSlimePredicate(p_223427_);
    }

    private SusSlimePredicate(MinMaxBounds.Ints size) {
        this.size = size;
    }

    public static @NotNull SusSlimePredicate fromJson(JsonObject p_223429_) {
        MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.fromJson(p_223429_.get("size"));
        return new SusSlimePredicate(minmaxbounds$ints);
    }


    @Override
    public boolean matches(Entity p_218828_, ServerLevel p_218829_, @Nullable Vec3 p_218830_) {
        if (p_218828_ instanceof SuspiciousSlime slime) {
            return this.size.matches(slime.getSize());
        } else {
            return false;
        }
    }

    @Override
    public JsonObject serializeCustomData() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.add("size", this.size.serializeToJson());
        return jsonobject;
    }

    @Override
    public Type type() {
        return SUS_SLIME;
    }
}
