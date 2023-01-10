package by.slizh.raytracing.frame;

import by.slizh.raytracing.entity.Plane;
import by.slizh.raytracing.entity.Solid;
import by.slizh.raytracing.entity.Sphere;
import by.slizh.raytracing.math.Ray;
import by.slizh.raytracing.math.RayHit;
import by.slizh.raytracing.math.Vector3;
import by.slizh.raytracing.rendering.Camera;
import by.slizh.raytracing.rendering.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ViewPort extends JPanel {
    private JFrame frame;
    private SettingsPanel settingsPanel;
    private Scene scene;
    private Robot robot;
    private float resolution = 0.8f;
    private float fov = 60;

    private boolean cameraCursorMoveFlag;

    private boolean solidPickFlag;
    private Solid pickedSolid;

    //private Vector3 lightPoint = new Vector3(1000f, 2000f, 1000f);
    private Vector3 lightPoint = new Vector3(0, 50, -100);

    private float kA = 0.2f; // коэффициент фонового освещения
    private Color colorA = new Color(255, 255, 255); // цвет фонового света
    private float kS = 1.0f; // коэффициент зеркального освещения
    private float a = 20; // коэффициент блеска поверхности
    private Color colorS = new Color(255, 255, 255); // цвет зеркального света

    private int RECURSION_NUMBER = 10;

    public ViewPort(JFrame container) {
        setFocusable(true);

        cameraCursorMoveFlag = false;

        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }

        // Processing key presses
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                Camera camera = scene.getCamera();
                float pitch = camera.getPitch();
                float yaw = camera.getYaw();

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_D ->
                            camera.setPosition(camera.getPosition().add(new Vector3(0.08f, 0.0f, 0.0f).rotate(pitch, yaw)));
                    case KeyEvent.VK_A ->
                            camera.setPosition(camera.getPosition().add(new Vector3(-0.08f, 0.0f, 0.0f).rotate(pitch, yaw)));
                    case KeyEvent.VK_W -> {
                        Vector3 moveVector = new Vector3(0.0f, 0.0f, 0.08f).rotate(pitch, yaw);
                        moveVector.setY(0.0f);
                        camera.setPosition(camera.getPosition().add(moveVector));
                    }
                    case KeyEvent.VK_S -> {
                        Vector3 moveVector = new Vector3(0.0f, 0.0f, -0.08f).rotate(pitch, yaw);
                        moveVector.setY(0.0f);
                        camera.setPosition(camera.getPosition().add(moveVector));
                    }
                    case KeyEvent.VK_E -> camera.setPosition(camera.getPosition().add(new Vector3(0.0f, 0.3f, 0.0f)));
                    case KeyEvent.VK_Q -> camera.setPosition(camera.getPosition().add(new Vector3(0.0f, -0.3f, 0.0f)));
                }

                repaint();
            }
        });

        // Processing camera mouse movement
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (cameraCursorMoveFlag) {

                    // Coefficient to convert integer mouse movements to radians
                    final float TO_RADIANS_COEF = (float) Math.PI / 4000;

                    int centerX = frame.getX() + frame.getWidth() / 2;
                    int centerY = frame.getY() + frame.getHeight() / 2;

                    int mouseXOffset = e.getXOnScreen() - centerX;
                    int mouseYOffset = e.getYOnScreen() - centerY;

                    if (mouseXOffset != 0 || mouseYOffset != 0) {
                        Camera camera = scene.getCamera();
                        camera.setPitch(Math.min((float) Math.PI / 2,
                                Math.max((float) (-Math.PI / 2), camera.getPitch() + mouseYOffset * TO_RADIANS_COEF)));
                        camera.setYaw(camera.getYaw() + mouseXOffset * TO_RADIANS_COEF);
                        repaint();

                        // Move mouse to the center
                        robot.mouseMove(centerX, centerY);
                    }


                }
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Left click
                if (SwingUtilities.isLeftMouseButton(e)) {
                    cameraCursorMoveFlag = !cameraCursorMoveFlag;
                }
                // Right click
                else {
                    int x = e.getX();
                    int y = e.getY();
                    float u;
                    float v;
                    if (getWidth() > getHeight()) {
                        u = (float) (x - getWidth() / 2 + getHeight() / 2) / getHeight() * 2 - 1;
                        v = -((float) y / getHeight() * 2 - 1);
                    } else {
                        u = (float) x / getWidth() * 2 - 1;
                        v = -((float) y - getHeight() / 2 + getWidth() / 2) / getWidth() * 2 - 1;
                    }
                    Camera camera = scene.getCamera();
                    float z = (float) (1 / Math.tan(camera.getFieldOfVision() / 2));
                    Vector3 rayDir = new Vector3(u, v, z).rotate(camera.getPitch(), camera.getYaw());
                    Ray ray = new Ray(camera.getPosition().subtract(new Vector3(0, 0, z)), rayDir);
                    RayHit rayHit = scene.calculateRayHit(ray);

                    // Picking solid
                    if (rayHit != null) {
                        if (pickedSolid == null) {
                            pickedSolid = rayHit.getSolid();
                            // Двигаем слайдеры на панели настроек
                            settingsPanel.sdReflectivity.setValue((int) (pickedSolid.getReflectivity() * 100));
                            settingsPanel.sdRed.setValue(pickedSolid.getColor().getRed());
                            settingsPanel.sdGreen.setValue(pickedSolid.getColor().getGreen());
                            settingsPanel.sdBlue.setValue(pickedSolid.getColor().getBlue());
                        } else {
                            if (pickedSolid.equals(rayHit.getSolid())) {
                                pickedSolid = null;
                                // Двигаем слайдеры на панели настроек
                                settingsPanel.sdReflectivity.setValue(0);
                                settingsPanel.sdRed.setValue(1);
                                settingsPanel.sdGreen.setValue(1);
                                settingsPanel.sdBlue.setValue(1);
                            } else {
                                pickedSolid = rayHit.getSolid();
                            }
                        }
                        repaint();
                    }
                }
            }
        });

        frame = container;
        scene = new Scene();

        //scene.addSolid(new Plane(new Vector3(0, -5, 0), Color.CYAN, true, 0.5f));
        scene.addSolid(new Sphere(new Vector3(0, 0, 1), Color.RED, 0.4f, 0.5f));
        scene.addSolid(new Sphere(new Vector3(1, 0, 1), Color.BLUE, 0.4f, 0.1f));
        scene.addSolid(new Sphere(new Vector3(0, 0, 5), Color.PINK, 2f, 1f));
        scene.addSolid(new Sphere(new Vector3(-1.3f, 0, 1), Color.GREEN, 0.1f, 0.5f));
        scene.addSolid(new Sphere(new Vector3(2,2,2),Color.CYAN,0.3f,0.5f));
        scene.addSolid(new Sphere(new Vector3(0, 0, 8), Color.YELLOW, 0.5f, 0.5f));


    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        int blockSize = (int) (1 / resolution);

        for (int x = 0; x < getWidth(); x += blockSize) {
            for (int y = 0; y < getHeight(); y += blockSize) {

                RayHit rayHit = findRayHit(x, y);

                // Если луч нашёл какой-либо объект
                if (rayHit != null) {
                    // Если пикнут солид, то обводим его желтым
                    if (rayHit.getSolid().equals(pickedSolid)) {
                        // Алгоритм обводки желтым
                        if (x > 0 && x < getWidth() - 1 && y > 0 && y < getHeight() - 1) {
                            boolean stopFlag = false;
                            for (int i = x - 1; i <= x + 1; i++) {
                                if (stopFlag) {
                                    break;
                                }
                                for (int j = y - 1; j <= y + 1; j++) {
                                    RayHit nearByHit = findRayHit(i, j);
                                    if (nearByHit == null || !nearByHit.getSolid().equals(pickedSolid)) {
                                        stopFlag = true;
                                        break;
                                    }
                                }
                            }
                            Color pixelColor;
                            if (stopFlag) {
                                pixelColor = Color.YELLOW;
                            } else {
                                pixelColor = calculatePixelColor(rayHit, RECURSION_NUMBER);
                            }
                            g.setColor(pixelColor);
                            g.fillRect(x, y, blockSize, blockSize);
                        }
                    } else {
                        Color pixelColor = calculatePixelColor(rayHit, RECURSION_NUMBER);
                        g.setColor(pixelColor);
                        g.fillRect(x, y, blockSize, blockSize);
                    }
//                    Vector3 toLightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition()));
//                    Ray toLightRay = new Ray(rayHit.getHitPosition(), toLightVector);
//
//                    // Считаем фоновое освещение
//                    Color color;
//                    Color iA = new Color((int) (kA * colorA.getRed()), (int) (kA * colorA.getBlue()), (int) (kA * colorA.getGreen()));
//                    Color iD;
//                    Color iS;
//
//                    // Если луч направленный к свету врезался в препядствие
//                    if (scene.calculateRayHit(toLightRay) != null) {
//                        // Оставляем только фоновое освещение
//                        color = iA;
//                    } else {
//                        Vector3 lightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition()));
//                        float cos = Vector3.dotProduct(rayHit.getNormal(), lightVector);
//                        float coef = Math.max(cos, 0.0f);
//                        iD = new Color((int) (rayHit.getSolid().getColor().getRed() * coef),
//                                (int) (rayHit.getSolid().getColor().getGreen() * coef),
//                                (int) (rayHit.getSolid().getColor().getBlue() * coef));
//                        Vector3 viewVector = rayHit.getRay().getDirection();
//                        //// TODO: 30.12.2022 зачем-то инвершу, наверное функция отражения через жопу написана
//                        Vector3 reflectLightVector = Vector3.inverse(Vector3.normalize(Vector3.reflect(lightVector, rayHit.getNormal())));
//                        cos = Vector3.dotProduct(reflectLightVector, viewVector);
//                        coef = Math.max(cos, 0.0f);
//                        iS = new Color((int) (colorS.getRed() * kS * Math.pow(coef, a)),
//                                (int) (colorS.getGreen() * kS * Math.pow(coef, a)),
//                                (int) (colorS.getBlue() * kS * Math.pow(coef, a)));
//                        color = new Color((int) ((iA.getRed() + iD.getRed() + iS.getRed()) / 3.0f),
//                                (int) ((iA.getGreen() + iD.getGreen() + iS.getGreen()) / 3.0f),
//                                (int) ((iA.getBlue() + iD.getBlue() + iS.getBlue()) / 3.0f));
//                    }
//                    g.setColor(color);
//                    g.fillRect(x, y, blockSize, blockSize);
                }
            }
        }
    }

    private RayHit findRayHit(int x, int y) {
        float u;
        float v;
        if (getWidth() > getHeight()) {
            u = (float) (x - getWidth() / 2 + getHeight() / 2) / getHeight() * 2 - 1;
            v = -((float) y / getHeight() * 2 - 1);
        } else {
            u = (float) x / getWidth() * 2 - 1;
            v = -((float) y - getHeight() / 2 + getWidth() / 2) / getWidth() * 2 - 1;
        }

        Camera camera = scene.getCamera();
        float z = (float) (1 / Math.tan(camera.getFieldOfVision() / 2));
        Vector3 rayDir = new Vector3(u, v, z).rotate(camera.getPitch(), camera.getYaw());
        Ray ray = new Ray(camera.getPosition().subtract(new Vector3(0, 0, z)), rayDir);

        return scene.calculateRayHit(ray);
    }

    public Color calculatePixelColor(RayHit rayHit, int recursionNumber) {
        Vector3 hitPos = rayHit.getHitPosition();
        Vector3 rayDir = rayHit.getRay().getDirection();
        Solid hitSolid = rayHit.getSolid();
        Color hitColor = hitSolid.getColor();
        float diffuseBrightness = getDiffuseBrightness(rayHit);
        float reflectivity = hitSolid.getReflectivity();

        Color reflectionColor;
        Vector3 reflectionVector = Vector3.normalize(Vector3.reflect(rayDir, rayHit.getNormal()));
        Vector3 reflectionRayOrigin = hitPos.add(reflectionVector.multiply(0.001f)); // Чтобы в тот же солид не попасть
        Ray reflectionRay = new Ray(reflectionRayOrigin, reflectionVector);

        RayHit reflectionHit = null;

        if (recursionNumber > 0) {
            reflectionHit = scene.calculateRayHit(reflectionRay);
        }

        // Рекурсия !!!
        if (reflectionHit != null) {
            reflectionColor = calculatePixelColor(reflectionHit, recursionNumber - 1);
        } else {
            reflectionColor = Color.WHITE;
        }

        Color color = multiplyColor(calcColor(hitColor, reflectionColor, reflectivity), diffuseBrightness);

//        Color specularColor = getSpecularColor(rayHit);
//        color = new Color((int) ((color.getRed() + specularColor.getRed() * reflectivity) / 2),
//                (int) ((color.getGreen() + specularColor.getGreen() * reflectivity) / 2),
//                (int) ((color.getBlue() + specularColor.getBlue() * reflectivity) / 2));

        return color;
    }

    private Color multiplyColor(Color color, float scalar) {
        scalar = Math.min(1, scalar);
        return new Color((int) (color.getRed() * scalar), (int) (color.getGreen() * scalar), (int) (color.getBlue() * scalar));
    }

    private float calcColor(float a, float b, float t) {
        return a + t * (b - a);
    }

    public Color calcColor(Color a, Color b, float t) {
        return new Color((int) calcColor(a.getRed(), b.getRed(), t), (int) calcColor(a.getGreen(), b.getGreen(), t), (int) calcColor(a.getBlue(), b.getBlue(), t));
    }

    private float getDiffuseBrightness(RayHit rayHit) {
        Vector3 toLightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition()));
        Ray toLightRay = new Ray(rayHit.getHitPosition(), toLightVector);
        RayHit lightBlockerHit = scene.calculateRayHit(toLightRay);

        if (lightBlockerHit != null && lightBlockerHit.getSolid() != rayHit.getSolid()) {
            return kA;
        } else {
            Vector3 lightVector = Vector3.normalize(lightPoint.subtract(rayHit.getHitPosition()));
            float cos = Vector3.dotProduct(rayHit.getNormal(), lightVector);
            float coef = Math.max(cos, 0.0f);
            return Math.max(coef, kA);
        }
    }

    private Color getSpecularColor(RayHit rayHit) {
        Vector3 cameraVector = Vector3.normalize(scene.getCamera().getPosition().subtract(rayHit.getHitPosition()));
        Vector3 lightVector = Vector3.normalize(rayHit.getHitPosition().subtract(lightPoint));
        Vector3 lightReflectedVector = Vector3.reflect(lightVector, rayHit.getNormal());
        float specularFactor = Math.max(0, Math.min(1, Vector3.dotProduct(lightReflectedVector, cameraVector)));
        return new Color((int) (colorS.getRed() * Math.pow(specularFactor, a) * rayHit.getSolid().getReflectivity()),
                (int) (colorS.getGreen() * Math.pow(specularFactor, a) * rayHit.getSolid().getReflectivity()),
                (int) (colorS.getBlue() * Math.pow(specularFactor, a) * rayHit.getSolid().getReflectivity()));
    }

    public void setResolution(float resolution) {
        this.resolution = resolution;
        repaint();
    }

    public void setFOV(int fov) {
        scene.getCamera().setFieldOfVision((float) Math.toRadians(fov));
        repaint();
    }

    public void setLightPoint(int x, int y, int z) {
        lightPoint.setX(x);
        lightPoint.setY(y);
        lightPoint.setZ(z);
        repaint();
    }

    public void setReflectivity(float reflectivity) {
        if (pickedSolid != null) {
            pickedSolid.setReflectivity(reflectivity);
            repaint();
        }
    }

    public void setRed(int red) {
        if (pickedSolid != null) {
            Color color = pickedSolid.getColor();
            pickedSolid.setColor(new Color(red, color.getGreen(), color.getBlue()));
            repaint();
        }
    }

    public void setGreen(int green) {
        if (pickedSolid != null) {
            Color color = pickedSolid.getColor();
            pickedSolid.setColor(new Color(color.getRed(), green, color.getBlue()));
            repaint();
        }
    }

    public void setBlue(int blue) {
        if (pickedSolid != null) {
            Color color = pickedSolid.getColor();
            pickedSolid.setColor(new Color(color.getRed(), color.getGreen(), blue));
            repaint();
        }
    }

    public void setSettingsPanel(SettingsPanel settingsPanel) {
        this.settingsPanel = settingsPanel;
    }
}
