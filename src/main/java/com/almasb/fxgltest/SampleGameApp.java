package com.almasb.fxgltest;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.extra.entity.components.OffscreenCleanComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import javafx.util.Duration;

import java.util.List;

import static com.almasb.fxgl.app.DSLKt.loopBGM;
import static com.almasb.fxgl.app.DSLKt.play;

public class SampleGameApp extends GameApplication {

    public enum DropType {
        DROPLET, BUCKET
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Drop");
        settings.setVersion("1.0");
        settings.setWidth(480);
        settings.setHeight(800);
        settings.setIntroEnabled(true);
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
        List<Entity> droplets = getGameWorld().getEntitiesByType(DropType.DROPLET);
        for (Entity droplet : droplets) {
            droplet.translateY(150 * tpf);
        }
    }

    private void spawnBucket() {
        Entities.builder()
                .at(getWidth() / 2, getHeight() - 200)
                .type(DropType.BUCKET)
                .viewFromTextureWithBBox("bucket.png")
                .with(new CollidableComponent(true))
                .with(new BucketComponent())
                .buildAndAttach();
    }

    private void spawnDroplet() {
        Entities.builder()
                .at(FXGLMath.random(getWidth() - 64), 0)
                .type(DropType.DROPLET)
                .viewFromTextureWithBBox("droplet.png")
                .with(new CollidableComponent(true))
                .with(new OffscreenCleanComponent())
                .buildAndAttach();
    }

    private class BucketComponent extends Component {

        @Override
        public void onUpdate(double tpf) {
            entity.setPosition(getInput().getMouseXWorld() - 32, getHeight() - 200);
        }
    }
}