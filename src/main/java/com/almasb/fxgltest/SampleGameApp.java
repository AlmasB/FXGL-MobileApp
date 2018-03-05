package com.almasb.fxgltest;

import com.almasb.fxgl.app.ApplicationMode;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Control;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.CollidableComponent;
import com.almasb.fxgl.entity.control.KeepOnScreenControl;
import com.almasb.fxgl.entity.control.OffscreenCleanControl;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.app.DSLKt.loopBGM;
import static com.almasb.fxgl.app.DSLKt.play;

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

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Drop");
        settings.setVersion("1.0");
        settings.setWidth(480);
        settings.setHeight(800);

        settings.setApplicationMode(ApplicationMode.DEVELOPER);
    }

    @Override
    protected void preInit() {
        loopBGM("bgm.mp3");
    }

    @Override
    protected void initGame() {
        spawnBucket();

        getMasterTimer().runAtInterval(() -> {
            spawnDroplet();
        }, Duration.seconds(1));
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(DropType.BUCKET, DropType.DROPLET) {
            @Override
            protected void onCollisionBegin(Entity bucket, Entity droplet) {
                droplet.removeFromWorld();

                play("drop.wav");
            }
        });
    }

    @Override
    protected void onUpdate(double tpf) {
        getGameWorld().getEntitiesByType(DropType.DROPLET).forEach(droplet -> droplet.translateY(150 * tpf));
    }

    private Entity spawnBucket() {
        return Entities.builder()
                .at(getWidth() / 2, getHeight() - 200)
                .type(DropType.BUCKET)
                .viewFromTextureWithBBox("bucket.png")
                .with(new CollidableComponent(true))
                .with(new BucketControl(), new KeepOnScreenControl(true, false))
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

    private class BucketControl extends Control {

        @Override
        public void onUpdate(Entity entity, double tpf) {
            entity.setPosition(getInput().getMouseXWorld() - 32, getHeight() - 200);
        }
    }
}