package com.almasb.fxgltest;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.entity.control.KeepOnScreenControl;
import com.almasb.fxgl.entity.control.OffscreenCleanControl;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Map;

/**
 * This is a proof-of-concept demo ONLY.
 * The code and API is for testing purposes ONLY.
 * The code will be updated when a stable FXGL mobile support is released.
 */
public class SampleGameApp extends GameApplication {

    /**
     * Types of entities in this game.
     */
    public enum DropType {
        DROPLET, BUCKET
    }

    private Entity bucket;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Drop");
        settings.setVersion("1.0");
        settings.setWidth(480);
        settings.setHeight(800);

        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void initInput() {
        UserAction moveLeft = new UserAction("Move Left") {
            @Override
            protected void onAction() {
                bucket.translateX(-200 * tpf());
            }
        };

        UserAction moveRight = new UserAction("Move Right") {
            @Override
            protected void onAction() {
                bucket.translateX(200 * tpf());
            }
        };

        // bind actions to keys
        getInput().addAction(moveLeft, KeyCode.A);
        getInput().addAction(moveRight, KeyCode.D);
    }

    @Override
    protected void initGame() {
        bucket = spawnBucket();

        getMasterTimer().runAtInterval(() -> {
            spawnDroplet();
        }, Duration.seconds(1));
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("left", false);
        vars.put("right", false);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(DropType.BUCKET, DropType.DROPLET) {
            @Override
            protected void onCollisionBegin(Entity bucket, Entity droplet) {
                droplet.removeFromWorld();
            }
        });
    }

    @Override
    protected void initUI() {
        Button left = new Button("LEFT");
        left.setPrefSize(50, 100);
        Button right = new Button("RIGHT");
        right.setPrefSize(50, 100);

        left.setTranslateY(getHeight() - 100);

        right.setTranslateX(getWidth() - 50);
        right.setTranslateY(getHeight() - 100);

        left.setOnMousePressed(e -> {
            getGameState().setValue("left", true);
        });

        left.setOnMouseReleased(e -> {
            getGameState().setValue("left", false);
        });

        right.setOnMousePressed(e -> {
            getGameState().setValue("right", true);
        });

        right.setOnMouseReleased(e -> {
            getGameState().setValue("right", false);
        });

        getGameScene().addUINodes(left, right);
    }

    @Override
    protected void onUpdate(double tpf) {

        if (getGameState().getBoolean("left")) {
            bucket.translateX(-200 * tpf());
        }

        if (getGameState().getBoolean("right")) {
            bucket.translateX(200 * tpf());
        }

        getGameWorld().getEntitiesByType(DropType.DROPLET).forEach(droplet -> droplet.translateY(150 * tpf));
    }

    private Entity spawnBucket() {
        return Entities.builder()
                .at(getWidth() / 2, getHeight() - 200)
                .type(DropType.BUCKET)
                .viewFromTextureWithBBox("bucket.png")
                .with(new CollidableComponent(true))
                .with(new KeepOnScreenControl(true, false))
                .buildAndAttach(getGameWorld());
    }

    private Entity spawnDroplet() {
        return Entities.builder()
                .at(FXGLMath.random(getWidth() - 64), 0)
                .type(DropType.DROPLET)
                .viewFromTextureWithBBox("droplet.png")
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanControl())
                .buildAndAttach(getGameWorld());
    }
}
