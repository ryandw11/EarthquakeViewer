package me.ryandw11.earthquake.components;

import org.kakara.engine.components.Component;
import org.kakara.engine.math.Vector3;

public class RotateWithEarth extends Component {
    @Override
    public void start() {

    }

    @Override
    public void update() {
        getTransform().setPosition(new Vector3(getTransform().getPosition().toJoml().rotateY((float) Math.toRadians(1))));
    }
}
