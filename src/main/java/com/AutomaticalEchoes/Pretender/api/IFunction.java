package com.AutomaticalEchoes.Pretender.api;

import com.AutomaticalEchoes.Pretender.common.entity.livingEntity.SuspiciousSlime.SuspiciousSlime;
import com.AutomaticalEchoes.Pretender.register.EntityRegister;
import com.AutomaticalEchoes.Pretender.register.ItemsRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;

public class IFunction{
    public interface FluidFunction {
        static boolean HurtArmor(LivingEntity livingEntity){
            int[] all = {0, 1, 2, 3};
            int[] part ={0,1};
            if(livingEntity instanceof Player player){
                player.getInventory().hurtArmor(DamageSource.GENERIC,4,player.isSwimming() && player.isSprinting()?  all: part);
                return true;
            }
            return false;
        }

        static boolean Transform(ItemEntity itemEntity){
            Item item = itemEntity.getItem().getItem();
            if(itemEntity.getAge() > 600 && (item instanceof ArmorItem || item instanceof TieredItem)){
                int i1 = itemEntity.getItem().getMaxDamage() - itemEntity.getItem().getDamageValue();
                int num = i1 / 100;
                int exp = i1 % 100;
                for (int i = 0; i < num; i++) {
                    ExperienceOrb experienceOrb = new ExperienceOrb(itemEntity.level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), 100);
                    itemEntity.level.addFreshEntity(experienceOrb);
                }
                ExperienceOrb experienceOrb = new ExperienceOrb(itemEntity.level, itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), exp);
                itemEntity.level.addFreshEntity(experienceOrb);
                itemEntity.discard();
                return true;
            }
            return false;
        }

        static boolean Summon(ItemEntity entity){
            if(entity.getItem().getItem() == ItemsRegister.SUSPICIOUS_SLIME_BALL.get()){
                entity.getItem().shrink(1);
                Level level = entity.level;
                SuspiciousSlime suspiciousSlime = EntityRegister.SUSPICIOUS_SLIME.get().create(level);
                suspiciousSlime.setPos(entity.position());
                level.addFreshEntity(suspiciousSlime);
                return true;
            }
            return false;
        }

        static boolean TransformOrSummon(ItemEntity entity){
            return Transform(entity) || Summon(entity);
        }

    }
    public interface BlockFunction{
        static Boolean EmptyWithSlime(BlockState p_54760_, BlockGetter p_54761_, BlockPos p_54762_, CollisionContext p_54763_) {
            if (p_54763_ instanceof EntityCollisionContext collisionContext && collisionContext.getEntity() != null) {
                return !(collisionContext.getEntity() instanceof SuspiciousSlime);
            }
            return true;
        }
    }
}

