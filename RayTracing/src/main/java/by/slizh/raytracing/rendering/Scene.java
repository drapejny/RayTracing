package by.slizh.raytracing.rendering;

import by.slizh.raytracing.entity.Solid;

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

    //// TODO: 28.12.2022 убрать геттеры и сеттеры для списка солидов
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
}
