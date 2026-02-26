package com.yuyuto.infinitymaxapi.gamelibs.physics;

public class MotionComponent {

    private Vector3 position;
    private Vector3 velocity;
    private Vector3 acceleration;
    private MovementType movementType;

    public MotionComponent(Vector3 position,
                           Vector3 velocity,
                           MovementType movementType) {

        this.position = position;
        this.velocity = velocity;
        this.movementType = movementType;

        if (movementType == MovementType.PROJECTILE) {
            this.acceleration = new Vector3(0, -9.81, 0); // 重力
        } else {
            this.acceleration = new Vector3(0, 0, 0);
        }
    }

    public void update(double deltaTime) {

        velocity = velocity.add(acceleration.multiply(deltaTime));
        position = position.add(velocity.multiply(deltaTime));
    }
}