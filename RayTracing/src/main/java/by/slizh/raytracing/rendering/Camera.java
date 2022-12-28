package by.slizh.raytracing.rendering;

import by.slizh.raytracing.Main;
import by.slizh.raytracing.math.Vector3;

public class Camera {
    private Vector3 position;
    private float yaw;
    private float pitch;
    private float fieldOfVision;

    public Camera() {
        this.position = new Vector3(0.0f, 0.0f, 0.0f);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
        this.fieldOfVision = (float) Math.PI / 3;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 position) {
        this.position = position;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getFieldOfVision() {
        return fieldOfVision;
    }

    public void setFieldOfVision(float fieldOfVision) {
        this.fieldOfVision = fieldOfVision;
    }
}
