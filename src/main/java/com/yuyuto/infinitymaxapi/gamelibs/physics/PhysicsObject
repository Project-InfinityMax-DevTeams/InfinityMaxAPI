package com.yuyuto.infinitymaxapi.gamelibs.physics;

public abstract class PhysicsObject {

    protected MotionComponent motion;
    protected PhysicalState physicalState;

    public PhysicsObject(MotionComponent motion,
                         PhysicalState physicalState) {
        this.motion = motion;
        this.physicalState = physicalState;
    }

    public void update(double deltaTime) {
        motion.update(deltaTime);
        onPhysicsUpdate(deltaTime);
    }

    protected abstract void onPhysicsUpdate(double deltaTime);

    public Vector3 getPosition() {
        return motion.getPosition();
    }

    public PhysicalState getPhysicalState() {
        return physicalState;
    }
}