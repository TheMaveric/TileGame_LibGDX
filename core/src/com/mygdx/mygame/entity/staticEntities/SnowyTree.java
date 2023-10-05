package com.mygdx.mygame.entity.staticEntities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.mygdx.mygame.Chunk;
import com.mygdx.mygame.entity.StaticEntity;
import com.mygdx.mygame.textures.Tile;
import com.mygdx.mygame.textures.TileTexture;

public class SnowyTree extends StaticEntity {
    TileTexture texture;
    Chunk chunk;
    Batch batch;

    public SnowyTree(float x, float y, float width, float height, Batch batch) {   /*for (int i = 0; i < staticEntities.size(); i++) {
                StaticEntity temp = staticEntities.get(i);
                int yy= (int)temp.boundingBox.x-6*Tile.TILEHEIGHT;
                int xx= (int)temp.boundingBox.y;
                if (yy >= xStart && yy <= xEnd && xx >= yStart && xx <= yEnd) {
                            batch.draw(texture.cactus,xx,yy, temp.boundingBox.height,temp.boundingBox.width);
                            batch.draw(texture.boundingBox, xx+(6*Tile.TILEWIDTH/4), yy, temp.boundingBox.height,temp.boundingBox.width);
                }
            }*/
        super(x + (6 * width / 4) - 6 * Tile.TILEWIDTH, y + 1.5f * Tile.TILEHEIGHT, width / 2, height / 4);//Height is /2 for hitbox Width comes out to 4*w/4 so unchanged
        texture = new TileTexture();
        chunk = new Chunk();
        this.batch = batch;
    }

    public void update() {
    }

    public void render() {
        batch.draw(texture.tree, boundingBox.x, boundingBox.y, 4 * Tile.TILEWIDTH, 4 * Tile.TILEHEIGHT);

    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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
