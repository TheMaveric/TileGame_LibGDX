package com.mygdx.mygame.entity;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.mygame.Chunk;
import com.mygdx.mygame.GameScreen;
import com.mygdx.mygame.MenuScreen;
import com.mygdx.mygame.World;
import com.mygdx.mygame.textures.Tile;
import com.mygdx.mygame.textures.TileTexture;

public class Player extends Entity {
    // Texture object
    TileTexture texture;
    // World
    Game game;
    GameScreen gameScreen;
    Chunk chunk;
    String prevkey = "";
    // Collision
    boolean collisionX, collisionY;
    // Vector
    private Vector2 velocity = new Vector2();
    // Stats
    private float speed, prevSpeed, gravity = 60 * 1.8f, animationTime = 0, increment;
    // Speed levels
    private float originalSpeed, quarterSpeed, halfSpeed, doubleSpeed, tripleSpeed;

    public Player(float x, float y, float width, float height, float speed, GameScreen gameScreen) {
        super(x, y, 3 * width / 4, 13 * height / 16, speed);
        texture = new TileTexture();
        this.gameScreen = gameScreen;
        this.speed = speed;
        originalSpeed = speed;
        quarterSpeed = speed / 4;
        halfSpeed = speed / 2;
        doubleSpeed = speed * 2;
        tripleSpeed = speed * 3;
        chunk = new Chunk();
    }

    public boolean movement(Batch batch, float deltaTime) {
        Boolean flag = false;
    /*if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W)) {
        prevkey="A";
        if(swimming==false) {
            batch.draw(texture.left.getKeyFrame(animationTime, true), pt.x,pt.y , Tile.TILEWIDTH+1,Tile.TILEHEIGHT);
        }
        else
            batch.draw(texture.swim_left.getKeyFrame(animationTime,true), pt.x,pt.y , Tile.TILEWIDTH+1,Tile.TILEHEIGHT);
        animationTime+=deltaTime;
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.W)) {
        prevkey="D";
        if(swimming==false)
            batch.draw(texture.right.getKeyFrame(animationTime,true), pt.x,pt.y , Tile.TILEWIDTH+1,Tile.TILEHEIGHT);
        else
            batch.draw(texture.swim_right.getKeyFrame(animationTime,true), pt.x,pt.y , Tile.TILEWIDTH+1,Tile.TILEHEIGHT);
        animationTime+=deltaTime;
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.S)) {
        prevkey="A";
        if(swimming==false)
            batch.draw(texture.left.getKeyFrame(animationTime,true), pt.x,pt.y , Tile.TILEWIDTH+1,Tile.TILEHEIGHT);
        else
            batch.draw(texture.swim_left.getKeyFrame(animationTime,true), pt.x,pt.y , Tile.TILEWIDTH+1,Tile.TILEHEIGHT);
        animationTime+=deltaTime;
    }
    else if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S)) {
        prevkey="D";
        if(swimming==false)
            batch.draw(texture.right.getKeyFrame(animationTime,true), pt.x,pt.y , Tile.TILEWIDTH+1,Tile.TILEHEIGHT);
        else
            batch.draw(texture.swim_right.getKeyFrame(animationTime,true), pt.x,pt.y , Tile.TILEWIDTH+1,Tile.TILEHEIGHT);
        animationTime+=deltaTime;
    }
    else */
        Vector2 pt = new Vector2((int) (boundingBox.x) - 2 * Tile.TILEWIDTH / 16, (int) boundingBox.y);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            flag = true;
            if (!swimming)
                batch.draw(
                        texture.standing.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            else if (velocity.x < 0)
                batch.draw(
                        texture.swim_left.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            else
                batch.draw(
                        texture.swim_right.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            animationTime += deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            flag = true;
            prevkey = "A";
            if (!swimming)
                batch.draw(
                        texture.left.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            else
                batch.draw(
                        texture.swim_left.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            animationTime += deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            flag = true;
            if (!swimming)
                batch.draw(
                        texture.standing.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            else if (velocity.x < 0)
                batch.draw(
                        texture.swim_left.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            else
                batch.draw(
                        texture.swim_right.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            animationTime += deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            flag = true;
            prevkey = "D";
            if (!swimming)
                batch.draw(
                        texture.right.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            else
                batch.draw(
                        texture.swim_right.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            animationTime += deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!swimming) {
                batch.draw(
                        texture.particle.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
                batch.draw(
                        texture.attack.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            }
            animationTime += deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            flag = true;
            if (!swimming) {
                batch.draw(
                        texture.particle.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
                batch.draw(
                        texture.attack.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            }
            animationTime += deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.TAB)) {
            flag = true;
            if (!swimming) {
                game.setScreen(new MenuScreen());
            } else {
                game.setScreen(gameScreen);
            }
            animationTime += deltaTime;
        } else {
            if (!swimming)
                batch.draw(
                        texture.standing.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            else if (prevkey.equals("A"))
                batch.draw(
                        texture.swim_left.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
            else
                batch.draw(
                        texture.swim_right.getKeyFrame(animationTime, true),
                        pt.x,
                        pt.y,
                        Tile.TILEWIDTH + 1,
                        Tile.TILEHEIGHT);
        }
        // bounding box
        // batch.draw(texture.boundingBox, (pt.x), pt.y,
        // boundingBox.getWidth(),boundingBox.getHeight());
        return flag;
    }

    public Vector2 cartToISO(Vector2 point) {
        // return new Vector2(point.x-point.y,(point.x+point.y)/2);
        return new Vector2(point.y, point.x);
    }

    public Vector2 isoToCART(Vector2 point) {
        return new Vector2((2 * point.y + point.x) / 2, (2 * point.y - point.x) / 2);
    }

    public void update(float delta, Batch batch) {
        // movement
        if (movement(batch, delta)) {
            // save old position
            float oldX = boundingBox.x,
                    oldY = boundingBox.y,
                    prevVelX = velocity.x,
                    prevVelY = velocity.y;
            collisionX = false;
            collisionY = false;
            // move on x and y
            boundingBox.setX(boundingBox.x + velocity.x * delta);
            boundingBox.setY(boundingBox.y + velocity.y * delta);
            // check collision [IMPORTANT! The rendering basically has inverted coordinates to normal XY
            // Coordatinate system, so X is replaced with Y and vice versa and same for width and height]
            playerCollision(oldX, oldY, prevVelX, prevVelY);
        }
        // update animation
        animationTime += delta;
    }

    private void playerCollision(float oldX, float oldY, float prevVelX, float prevVelY) {
        for (int i = 0; i < World.staticEntities.size(); i++) {
            // HERE YOU HAVE CHANGED!!!
            Rectangle rectangle =
                    new Rectangle(
                            World.staticEntities.get(i).boundingBox.y,
                            World.staticEntities.get(i).boundingBox.x,
                            World.staticEntities.get(i).boundingBox.height,
                            World.staticEntities.get(i).boundingBox.width);
            Vector2 pt = new Vector2(boundingBox.x, boundingBox.y);
            Rectangle playerBound =
                    new Rectangle(pt.x, pt.y, boundingBox.getHeight(), boundingBox.getWidth());
            if (playerBound.x - rectangle.x < 4 * Tile.TILEWIDTH
                    && playerBound.y - rectangle.y < 4 * Tile.TILEHEIGHT
                    && checkCollision(rectangle)) {
                System.out.println("COLLISION");

                collisionX =
                        rectangle.contains(playerBound.x, oldY)
                                || rectangle.contains(playerBound.x + playerBound.width, oldY)
                                || rectangle.contains(playerBound.x, oldY + playerBound.height)
                                || rectangle.contains(playerBound.x + playerBound.width, oldY + playerBound.height);
                if (collisionX) {
                    boundingBox.setX(oldX);
                    // velocity.x = 0;
                }
                collisionY =
                        rectangle.contains(oldX, playerBound.y)
                                || rectangle.contains(oldX + playerBound.width, playerBound.y)
                                || rectangle.contains(oldX, playerBound.y + playerBound.height)
                                || rectangle.contains(oldX + playerBound.width, playerBound.y + playerBound.height);
                if (collisionY) {
                    boundingBox.setY(oldY);
                    // velocity.y = 0;
                }
                if (collisionX && collisionY) {
                    boundingBox.setY(oldY);
                    boundingBox.setX(oldX - 1);
                }
            } else {
                if (collisionX) {
                    velocity.x = prevVelX;
                }
                if (collisionY) {
                    velocity.y = prevVelY;
                }
            }
        }
    }

    private boolean checkCollision(Rectangle rectangle) {
        Vector2 pt = new Vector2(boundingBox.x, boundingBox.y);
        Rectangle playerBound =
                new Rectangle(pt.x, pt.y, boundingBox.getHeight(), boundingBox.getWidth());
        return playerBound.overlaps(rectangle);
    }

    public void draw(SpriteBatch batch, float deltaTime) {
        update(Gdx.graphics.getDeltaTime(), batch);
        super.draw(batch);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void setSwimming(boolean swimming) {
        if (swimming == true) speed = halfSpeed;
        else speed = originalSpeed;
        this.swimming = swimming;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                velocity.y = +speed;
                break;
            case Input.Keys.S:
                velocity.y = -speed;
                break;
            case Input.Keys.A:
                velocity.x = -speed;
                break;
            case Input.Keys.D:
                velocity.x = speed;
                break;
            case Input.Keys.LEFT:
                if (velocity.x > 0) boundingBox.setX(boundingBox.x + Tile.TILEWIDTH);
                else if (velocity.x < 0) boundingBox.setX(boundingBox.x - Tile.TILEWIDTH);
                if (velocity.y > 0) boundingBox.setY(boundingBox.y + Tile.TILEHEIGHT);
                else if (velocity.y < 0) boundingBox.setY(boundingBox.y - Tile.TILEHEIGHT);
                break;
            case Input.Keys.RIGHT:
                if (velocity.x < 0) boundingBox.setX(boundingBox.x + Tile.TILEWIDTH);
                else if (velocity.x > 0) boundingBox.setX(boundingBox.x - Tile.TILEWIDTH);
                if (velocity.y < 0) boundingBox.setY(boundingBox.y + Tile.TILEHEIGHT);
                else if (velocity.y > 0) boundingBox.setY(boundingBox.y - Tile.TILEHEIGHT);
                break;
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.D:
                velocity.x = 0;
                animationTime = 0;
                break;
            case Input.Keys.W:
            case Input.Keys.S:
                velocity.y = 0;
                animationTime = 0;
                break;
        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
