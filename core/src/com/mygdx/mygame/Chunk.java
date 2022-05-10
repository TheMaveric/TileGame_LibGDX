package com.mygdx.mygame;

import com.mygdx.mygame.terrain.Terrain;
import com.mygdx.mygame.textures.Tile;

public class Chunk {
    public String chunkFinder(float x,float y)
    {
        int xChunk=(int)x/(Terrain.width*Tile.TILEWIDTH);
        int yChunk=(int)y/(Terrain.height*Tile.TILEHEIGHT);
        if(x<0)
        {
            xChunk--;
        }
        if(y<0)
        {
            yChunk--;
        }
        return xChunk+" "+yChunk;
    }
}
