package com.mygdx.mygame.textures;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.HashMap;
import java.util.Map;

public class TileTexture {
    //Atlas
    public static TextureAtlas atlas=new TextureAtlas("textureAtlas.atlas");

    //Animations
    public Animation<TextureRegion> attack =
            new Animation<TextureRegion>(0.4f, atlas.findRegions("attack"), PlayMode.LOOP);
    public Animation<TextureRegion> left =
            new Animation<TextureRegion>(0.33f, atlas.findRegions("left"), PlayMode.LOOP);
    public Animation<TextureRegion> right =
            new Animation<TextureRegion>(0.33f, atlas.findRegions("right"), PlayMode.LOOP);
    public Animation<TextureRegion> particle =
            new Animation<TextureRegion>(0.3f, atlas.findRegions("particle"), PlayMode.LOOP);
    public Animation<TextureRegion> slime_left =
            new Animation<TextureRegion>(0.033f, atlas.findRegions("slime_left"), PlayMode.LOOP);
    public Animation<TextureRegion> slime_right =
            new Animation<TextureRegion>(0.033f, atlas.findRegions("slime_right"), PlayMode.LOOP);
    public Animation<TextureRegion> small_particle =
            new Animation<TextureRegion>(0.033f, atlas.findRegions("small_particle"), PlayMode.LOOP);
    public Animation<TextureRegion> standing =
            new Animation<TextureRegion>(0.33f, atlas.findRegions("standing"), PlayMode.LOOP);
    public Animation<TextureRegion> swim_left =
            new Animation<TextureRegion>(0.33f, atlas.findRegions("swim_left"), PlayMode.LOOP);
    public Animation<TextureRegion> swim_right =
            new Animation<TextureRegion>(0.33f, atlas.findRegions("swim_right"), PlayMode.LOOP);
    public Animation<TextureRegion> water_t =
            new Animation<TextureRegion>(0.2f, atlas.findRegions("water_t"), PlayMode.LOOP);

    //BIOME
    public TextureRegion badlands=atlas.findRegion("badlands");
    public TextureRegion badlandsHills=atlas.findRegion("badlandsHills");
    public TextureRegion badlandsPlateau=atlas.findRegion("badlandsPlateau");
    public TextureRegion beach=atlas.findRegion("beach");
    public TextureRegion snowyBeach=atlas.findRegion("snowyBeach");
    public TextureRegion shallowWater=atlas.findRegion("shallowWater");
    public TextureRegion frozenShallowWater=atlas.findRegion("frozenShallowWater");
    public TextureRegion deepWater=atlas.findRegion("deepWater");
    public TextureRegion frozenDeepWater=atlas.findRegion("frozenDeepWater");
    public TextureRegion desert=atlas.findRegion("desert");
    public TextureRegion desertHills=atlas.findRegion("desertHills");
    public TextureRegion forest=atlas.findRegion("forest");
    public TextureRegion jungle=atlas.findRegion("jungle");
    public TextureRegion jungleHills=atlas.findRegion("jungleHills");
    public TextureRegion mountains=atlas.findRegion("mountains");
    public TextureRegion snowyMountains=atlas.findRegion("snowyMountains");
    public TextureRegion plain=atlas.findRegion("plain");
    public TextureRegion rainforest=atlas.findRegion("rainforest");
    public TextureRegion rainforestHills=atlas.findRegion("rainforestHills");
    public TextureRegion rainforestMountains=atlas.findRegion("rainforestMountains");
    public TextureRegion savanna=atlas.findRegion("savanna");
    public TextureRegion savannaPlateau=atlas.findRegion("savannaPlateau");
    public TextureRegion swamp=atlas.findRegion("swamp");
    public TextureRegion swampHills=atlas.findRegion("swampHills");
    public TextureRegion taiga=atlas.findRegion("taiga");
    public TextureRegion taigaHills=atlas.findRegion("taigaHills");
    public TextureRegion taigaMountains=atlas.findRegion("taigaMountains");
    public TextureRegion snowyTaiga=atlas.findRegion("snowyTaiga");
    public TextureRegion snowyTaigaHills=atlas.findRegion("snowyTaigaHills");
    public TextureRegion snowyTaigaMountains=atlas.findRegion("snowyTaigaMountains");
    public TextureRegion snowyTundra=atlas.findRegion("snowyTundra");
    public TextureRegion iceSpikes=atlas.findRegion("iceSpikes");

    //UI
    public TextureRegion button=atlas.findRegion("button");
    public TextureRegion button_down=atlas.findRegion("button_down");
    public TextureRegion inventory=atlas.findRegion("inventory_slot");
    public TextureRegion special_slot=atlas.findRegion("special_slot");
    public TextureRegion heart=atlas.findRegion("heart");
    public TextureRegion pebble=atlas.findRegion("pebble");
    public TextureRegion redtrans=atlas.findRegion("redtrans");
    public TextureRegion window=atlas.findRegion("window_bg");
    public TextureRegion boundingBox=atlas.findRegion("boundingBox");

    //Static Entity
    public TextureRegion cactus=atlas.findRegion("cactus");
    public TextureRegion deadbush=atlas.findRegion("deadbush");
    public TextureRegion flower=atlas.findRegion("flower");
    public TextureRegion grass=atlas.findRegion("grass");
    public TextureRegion tree=atlas.findRegion("tree");
    public TextureRegion snowyTree=atlas.findRegion("snowyTree");
    public TextureRegion rock=atlas.findRegion("rock");
    public TextureRegion slime=atlas.findRegion("slime");
    public TextureRegion stick=atlas.findRegion("stick");
    public TextureRegion projectile=atlas.findRegion("projectile");
    public TextureRegion slingshot=atlas.findRegion("slingshot");
    public Map<Integer,TextureRegion> map=new HashMap<>();
    public TileTexture()
    {
        map.put(0,deepWater);
        map.put(1,shallowWater);
        map.put(2,frozenDeepWater);
        map.put(3,frozenShallowWater);
        map.put(4,beach);
        map.put(5,snowyBeach);
        map.put(6,desert);
        map.put(7,desertHills);
        map.put(8,badlands);
        map.put(9,badlandsPlateau);
        map.put(10,badlandsHills);
        map.put(11,taiga);
        map.put(12,taigaHills);
        map.put(13,taigaMountains);
        map.put(14,snowyTaiga);
        map.put(15,snowyTaigaHills);
        map.put(16,snowyTaigaMountains);
        map.put(17,savanna);
        map.put(18,savannaPlateau);
        map.put(19,jungle);
        map.put(20,jungleHills);
        map.put(21,swamp);
        map.put(22,swampHills);
        map.put(23,plain);
        map.put(24,forest);
        map.put(25,rainforest);
        map.put(26,rainforestHills);
        map.put(27,rainforestMountains);
        map.put(28,mountains);
        map.put(29,snowyTundra);
        map.put(30,snowyMountains);
        map.put(31,iceSpikes);
    }


}

