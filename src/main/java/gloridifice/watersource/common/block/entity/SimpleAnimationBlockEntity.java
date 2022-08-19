package gloridifice.watersource.common.block.entity;

import com.mojang.math.Quaternion;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class SimpleAnimationBlockEntity extends ModNormalBlockEntity{
    float animTime = 0f;
    float scale = 0;
    Quaternion quaternion = new Quaternion(0,0,0,true);

    public float getAnimTime() {
        return animTime;
    }

    public float getScale() {
        return scale;
    }

    public Quaternion getQuaternion() {
        return quaternion;
    }

    public void setQuaternion(Quaternion quaternion) {
        this.quaternion = quaternion;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setAnimTime(float animTime) {
        this.animTime = animTime;
    }
    public SimpleAnimationBlockEntity(BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state) {
        super(blockEntityType, pos, state);
    }
}
