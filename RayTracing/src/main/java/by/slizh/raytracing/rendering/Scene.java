package by.slizh.raytracing.rendering;

import by.slizh.raytracing.entity.Solid;
import by.slizh.raytracing.math.Ray;
import by.slizh.raytracing.math.RayHit;
import by.slizh.raytracing.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Scene {

    private Camera camera;
    private List<Solid> solids;

    public Scene() {
        this.solids = new ArrayList<>();
        this.camera = new Camera();
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public List<Solid> getSolids() {
        return solids;
    }

    public void setSolids(List<Solid> solids) {
        this.solids = solids;
    }

    public void addSolid(Solid solid) {
        solids.add(solid);
    }

    public void clearSolids() {
        solids.clear();
    }

    public RayHit calculateRayHit(Ray ray) {
        RayHit rayHit = null;
        for (Solid solid : solids) {
            Vector3 intersection = solid.calculateIntersection(ray);
            if (intersection != null &&
                    (rayHit == null || Vector3.distance(rayHit.getHitPosition(), ray.getOrigin()) > Vector3.distance(intersection, ray.getOrigin()))) {
                rayHit = new RayHit(intersection, ray, solid.calculateNormal(ray), solid);
            }
        }
        return rayHit;
    }
}
