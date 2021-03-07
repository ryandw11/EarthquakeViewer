package me.ryandw11.earthquake;

import me.ryandw11.earthquake.components.RotateWithEarth;
import org.kakara.engine.GameHandler;
import org.kakara.engine.gameitems.GameItem;
import org.kakara.engine.gameitems.Texture;
import org.kakara.engine.gameitems.mesh.Mesh;
import org.kakara.engine.input.Input;
import org.kakara.engine.input.controller.ids.ControllerID;
import org.kakara.engine.input.controller.ids.GamePadAxis;
import org.kakara.engine.input.controller.ids.GamePadButton;
import org.kakara.engine.input.key.KeyCode;
import org.kakara.engine.input.mouse.MouseInput;
import org.kakara.engine.math.Vector3;
import org.kakara.engine.models.StaticModelLoader;
import org.kakara.engine.resources.ResourceManager;
import org.kakara.engine.scene.AbstractGameScene;
import org.kakara.engine.utils.RGBA;
import org.kakara.engine.utils.Time;

public class MainScene extends AbstractGameScene {

    private RESTHandler restHandler;

    public MainScene(GameHandler gameHandler) {
        super(gameHandler);
    }

    GameItem earth, item;

    @Override
    public void work() {
        this.restHandler = new RESTHandler();
        restHandler.updateList();
    }

    @Override
    public void loadGraphics(GameHandler gameHandler) throws Exception {
        ResourceManager rm = gameHandler.getResourceManager();

        Mesh[] earthMesh = StaticModelLoader.load(rm.getResource("/earth/Earth.obj"), "/earth", this, rm);
        earth = new GameItem(earthMesh);
        earthMesh[0].getMaterial().get().setNormalMap(new Texture(rm.getResource("/earth/earth_norm.jpg"), this));
        earth.transform.setPosition(0, 0, 0);
        earth.transform.setScale(0.02f);

        Mesh[] sphereMesh = StaticModelLoader.load(rm.getResource("/sphere.obj"), "/", this, rm);
        sphereMesh[0].getMaterial().get().setAmbientColor(new RGBA(255, 0, 0, 1));

        float r = 500f * 0.02f;

        for (Earthquake earthquake : restHandler.getCurrentQuakes()) {
            GameItem item = new GameItem(sphereMesh);
            item.transform.setScale(0.2f);
            item.addComponent(RotateWithEarth.class);

            double lon = Math.toRadians(earthquake.getLongitude());
            double lat = Math.toRadians(earthquake.getLatitude());
            double x = -r * Math.cos(lat) * Math.sin(lon);
            double y = -r * Math.cos(lat) * Math.cos(lon);
            double z = r * Math.sin(lat);
            item.transform.setPosition((float) x, (float) z, (float) y);

            add(item);
        }


        add(earth);

        getCamera().setPosition(0, 0, 20);
    }

    private Vector3 rotation = new Vector3();

    @Override
    public void update(float v) {
        // Controller
        if (getControllerManager().controllerExists(ControllerID.CONTROLLER_ONE)) {
            float moveZ = Time.getDeltaTime() * 15f * Input.getGamePadAxis(ControllerID.CONTROLLER_ONE, GamePadAxis.LEFT_STICK_Y);
            float moveX = Time.getDeltaTime() * 15f * Input.getGamePadAxis(ControllerID.CONTROLLER_ONE, GamePadAxis.LEFT_STICK_X);
            // Move the position in the Z direction using the Left Stick
            getCamera().movePosition(0, 0, moveZ);
            // Move the position in the X direction using the Left Stick
            getCamera().movePosition(moveX, 0, 0);
            // Rotate the camera based upon the Right Stick.
            getCamera().moveRotation(Time.getDeltaTime() * 150f * Input.getGamePadAxis(ControllerID.CONTROLLER_ONE, GamePadAxis.RIGHT_STICK_Y),
                    Time.getDeltaTime() * 150f * Input.getGamePadAxis(ControllerID.CONTROLLER_ONE, GamePadAxis.RIGHT_STICK_X), 0);

            // Move the Camera up in the Y Direction.
            if (Input.isGamePadButtonDown(ControllerID.CONTROLLER_ONE, GamePadButton.A)) {
                getCamera().movePosition(0, 15f * Time.getDeltaTime(), 0);
            }
            // Move the Camera down in the Y Direction.
            if (Input.isGamePadButtonDown(ControllerID.CONTROLLER_ONE, GamePadButton.B)) {
                getCamera().movePosition(0, -15f * Time.getDeltaTime(), 0);
            }

            // Exit the game.
            if (Input.isGamePadButtonDown(ControllerID.CONTROLLER_ONE, GamePadButton.START))
                gameHandler.exit();
        }

        if (Input.isKeyDown(KeyCode.W)) {
            earth.getTransform().getRotation().rotateZ((float) Math.toRadians(1));
        }
        if (Input.isKeyDown(KeyCode.S)) {
            earth.getTransform().getRotation().rotateZ((float) -Math.toRadians(1));
        }
        if (Input.isKeyDown(KeyCode.A)) {
            earth.getTransform().getRotation().rotateY((float) Math.toRadians(1));
        }
        if (Input.isKeyDown(KeyCode.D)) {
            earth.getTransform().getRotation().rotateY((float) -Math.toRadians(1));
        }
//        if (Input.isKeyDown(KeyCode.SPACE)) {
//            getCamera().movePosition(0, Time.getDeltaTime() * 15f, 0);
//        }
//        if (Input.isKeyDown(KeyCode.LEFT_SHIFT)) {
//            getCamera().movePosition(0, Time.getDeltaTime() * -15f, 0);
//        }
        if (Input.isKeyDown(KeyCode.ESCAPE)) {
            gameHandler.exit();
        }

//        setCursorStatus();

        MouseInput mouseInput = gameHandler.getMouseInput();
        rotation.addMut((float) (Time.getDeltaTime() * 1 * (mouseInput.getDeltaPosition().y)), (float) (Time.getDeltaTime() * 1 * mouseInput.getDeltaPosition().x), 0);
//        earth.getTransform().getRotation().identity().rotateZ(rotation.x);
//        earth.getTransform().getRotation().rotateY(rotation.y);

        earth.transform.getRotation().rotateY((float) Math.toRadians(1));
//        item.transform.setPosition(new Vector3(item.transform.getPosition().toJoml().rotateY((float) Math.toRadians(1))));
    }
}
