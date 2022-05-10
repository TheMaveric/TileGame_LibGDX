package com.mygdx.mygame.terrain;
import java.awt.*;
import java.awt.image.BufferedImage;
public class Biome {
    //private static final Color plain = new Color(80, 255,23);
    //private static final Color forest = new Color(31, 120, 31);
    public BufferedImage image;
    private static final Color deepWater = new Color(20, 91, 134);
    private static final Color shallowWater = new Color(38, 166, 245);
    private static final Color frozenDeepWater = new Color(20, 123, 174); //only for tundra
    private static final Color frozenShallowWater = new Color(37, 174,255); //only for tundra

    private static final Color beach = new Color(255, 216,122);
    private static final Color snowyBeach = new Color(250, 240, 191);

    private static final Color desert = new Color(250, 148,24);
    private static final Color desertHills = new Color(210, 95, 17);

    private static final Color badlands = new Color(217, 69, 21);
    private static final Color badlandsPlateau = new Color(202, 140, 101);
    private static final Color badlandsHills = new Color(120, 25, 25);

    private static final Color taiga = new Color(10, 102, 89);
    private static final Color taigaHills = new Color(22, 57, 51);
    private static final Color taigaMountains = new Color(51, 142, 129);
    private static final Color snowyTaiga = new Color(49, 85, 74);
    private static final Color snowyTaigaHills = new Color(36, 63, 54);
    private static final Color snowyTaigaMountains = new Color(89, 125, 114);

    private static final Color savanna = new Color(189, 178, 95);
    private static final Color savannaPlateau = new Color(167, 157, 100);

    private static final Color jungle = new Color(83, 123, 9);
    private static final Color jungleHills = new Color(44, 66, 4);

    private static final Color swamp = new Color(48, 53, 40);
    private static final Color swampHills = new Color(31, 36, 24);

    private static final Color plain = new Color(141, 179, 96);
    private static final Color forest = new Color(5, 102, 33);

    //private static final Color rainforest = new Color(0, 66, 44);
    private static final Color forestHills = new Color(0, 66, 44);
    //private static final Color rainforestHills = new Color(0, 48, 31);
    private static final Color forestMountains = new Color(0, 48, 31);
    //private static final Color rainforestMountains = new Color(0, 27, 11);

    private static final Color mountain = new Color(96, 96, 96);

    private static final Color snowyTundra =  new Color(255, 255, 255);
    private static final Color snowyMountains =  new Color(160,160,160);
    private static final Color iceSpikes =  new Color(180, 220, 220);

    private static final Color entity =  new Color(0, 0, 0);

    public int[][] biome=new int[Terrain.width][Terrain.height];
    public static Color darker(Color c, float factor) {
        return blend(c, Color.BLACK, factor);
    }
    public static Color blend(Color cFrom, Color cTo, float factor) {
        if (factor < 0f || factor > 1f) {
            throw new IllegalArgumentException("factor not between 0 and 1: " + factor);
        }

        float[] rgbaFrom = cFrom.getRGBComponents(null);
        float[] rgbaTo = cTo.getRGBComponents(null);

        rgbaFrom[0] += (rgbaTo[0] - rgbaFrom[0]) * factor;
        rgbaFrom[1] += (rgbaTo[1] - rgbaFrom[1]) * factor;
        rgbaFrom[2] += (rgbaTo[2] - rgbaFrom[2]) * factor;

        return new Color(rgbaFrom[0], rgbaFrom[1], rgbaFrom[2], rgbaFrom[3]);
    }
    float normalize(float value, float min, float max) {
        return (1 - ((value - min) / (max - min)))*0.5f;
    }
    private  Color makeDeepWater(int i, int j,int temperatureValue,int islandValue)
    {
        if(temperatureValue<=25)
        {
            biome[i][j]=2;
            return blend(frozenDeepWater,Color.BLACK,normalize(islandValue,255,0));
        }
        biome[i][j]=0;
        return blend(deepWater,Color.BLACK,normalize(islandValue,255,0));
    }
    private  Color makeShallowWater(int riverValue,int i, int j, int temperatureValue,int islandValue)
    {
        if(riverValue>248)
        {
            if(temperatureValue<=25)
            {
                biome[i][j]=2;
                return blend(frozenDeepWater,Color.BLACK,normalize(islandValue,255,0));
            }
            biome[i][j]=0;
            return blend(deepWater,Color.BLACK,normalize(islandValue,255,0));
        }
        else if(temperatureValue<=25)
        {
            biome[i][j]=3;
            return blend(frozenShallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=1;
        return blend(shallowWater,Color.BLACK,normalize(islandValue,255,0));
    }
    /*private Color makeBeach(int islandValue,int humidityValue,int temperatureValue,int riverValue) {
        if(riverValue>248)
        {
            return deepWater;
        }
        else if(riverValue>245)
        {
            return shallowWater;
        }
            return beach;

    }*/
    private  Color makeIceSpikes(int islandValue,int riverValue,int i, int j)
    {
        islandValue=(int)Math.pow(islandValue,4);
        islandValue = islandValue * (255 - 0) / 2 + (255 + 0) / 2;
        if(islandValue<100) {
            biome[i][j]=31;
            return blend(iceSpikes,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>248)
        {
            biome[i][j]=2;
            return blend(frozenDeepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=3;
            return blend(frozenShallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=29;
        return blend(snowyTundra,Color.BLACK,normalize(islandValue,0,255));
    }
    private  Color makeTundra(int islandValue,int riverValue,int i, int j)
    {
        if(islandValue<=50)
        {
            biome[i][j]=30;
            return blend(snowyMountains,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>248)
        {
            biome[i][j]=3;
            return blend(frozenDeepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=4;
            return blend(frozenShallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=5;
            return blend(snowyBeach,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=29;
        return blend(snowyTundra,Color.BLACK,normalize(islandValue,0,255));
    }
    private  Color makeTaiga(int islandValue,int temperatureValue,int riverValue,int i, int j)
    {
        if(islandValue<=30)
        {
            biome[i][j]=29;
            return blend(snowyTundra,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>30 && islandValue<=50)
        {
            if(temperatureValue<25)
            {
                biome[i][j]=30;
                return blend(snowyTaigaMountains,Color.BLACK,normalize(islandValue,0,255));
            }
            else {
                biome[i][j]=13;
                return blend(taigaMountains,Color.BLACK,normalize(islandValue,0,255));
            }
        }
        else if(islandValue>50 && islandValue<=70)
        {
            if(temperatureValue<25)
            {
                biome[i][j]=15;
                return blend(snowyTaigaHills,Color.BLACK,normalize(islandValue,0,255));
            }
            else {
                biome[i][j]=12;
                return blend(taigaHills,Color.BLACK,normalize(islandValue,0,255));
            }
        }
        else if(riverValue>248)
        {
            biome[i][j]=0;
            return blend(deepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=1;
            return blend(shallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>70 && islandValue<=115)
        {
            if(temperatureValue<25)
            {
                biome[i][j]=14;
                return blend(snowyTaiga,Color.BLACK,normalize(islandValue,0,255));
            }
            else {
                biome[i][j]=11;
                return blend(taiga,Color.BLACK,normalize(islandValue,0,255));
            }
        }
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=4;
            return blend(beach,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=11;
        return blend(taiga,Color.BLACK,normalize(islandValue,0,255));
    }
    private  Color makeDesert(int islandValue,int riverValue,int i, int j)
    {
        if(islandValue<=50)
        {
            biome[i][j]=7;
            return blend(desertHills,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>248)
        {
            biome[i][j]=0;
            return blend(deepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=1;
            return blend(shallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=4;
            return blend(beach,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=6;
        return blend(desert,Color.BLACK,normalize(islandValue,0,255));
    }
    private  Color makeBadlands(int islandValue,int riverValue,int i, int j)
    {
        if(islandValue<=50)
        {
            biome[i][j]=10;
            return blend(badlandsHills,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>248)
        {
            biome[i][j]=0;
            return blend(deepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=1;
            return blend(shallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        /*else if(islandValue>50 && islandValue<80)
        {
            return badlandsPlateau;
        }*/
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=4;
            return blend(beach,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=8;
        return blend(badlands,Color.BLACK,normalize(islandValue,0,255));
    }
    /*private  Color makeRainforest(int islandValue,int riverValue,int i, int j)
    {
        if(islandValue<=30)
        {
            biome[i][j]=27;
            return rainforestMountains;
        }
        else if(islandValue>30 && islandValue<=50)
        {
            biome[i][j]=26;
            return rainforestHills;
        }
        else if(riverValue>248)
        {
            biome[i][j]=0;
            return deepWater;
        }
        else if(riverValue>245)
        {
            biome[i][j]=1;
            return shallowWater;
        }
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=4;
            return beach;
        }
        biome[i][j]=25;
        return rainforest;
    }*/
    private  Color makeForest(int islandValue,int riverValue,int i, int j)
    {

        if(islandValue<=30)
        {
            biome[i][j]=28;
            return blend(forestMountains,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>30 && islandValue<=50)
        {
            biome[i][j]=25;
            return blend(forestHills,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>248)
        {
            biome[i][j]=0;
            return blend(deepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=1;
            return blend(shallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=4;
            return blend(beach,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=24;
        return blend(forest,Color.BLACK,normalize(islandValue,0,255));
    }
    private  Color makeSavanna(int islandValue,int riverValue,int i, int j)
    {
        if(islandValue<=30)
        {
            biome[i][j]=18;
            return blend(savannaPlateau,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>248)
        {
            biome[i][j]=0;
            return blend(deepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=1;
            return blend(shallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=4;
            return blend(beach,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=17;
        return blend(savanna,Color.BLACK,normalize(islandValue,0,255));
    }
    private  Color makePlain(int islandValue,int riverValue,int i, int j)
    {
        if(islandValue<=30)
        {
            biome[i][j]=29;
            return blend(snowyTundra,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>30 && islandValue<=50)
        {
            biome[i][j]=28;
            return blend(mountain,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>248)
        {
            biome[i][j]=0;
            return blend(deepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=1;
            return blend(shallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=4;
            return blend(beach,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=23;
        return blend(plain,Color.BLACK,normalize(islandValue,0,255));
    }
    private  Color makeJungle(int islandValue,int riverValue,int i, int j)
    {
        if(islandValue<=50)
        {
            biome[i][j]=20;
            return blend(jungleHills,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>248)
        {
            biome[i][j]=0;
            return blend(deepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=1;
            return blend(shallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=4;
            return blend(beach,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=19;
        return blend(jungle,Color.BLACK,normalize(islandValue,0,255));
    }
    private  Color makeSwamp(int islandValue,int riverValue,int i, int j)
    {
        if(islandValue<=50)
        {
            biome[i][j]=22;
            return blend(swampHills,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>248)
        {
            biome[i][j]=0;
            return blend(deepWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(riverValue>245)
        {
            biome[i][j]=1;
            return blend(shallowWater,Color.BLACK,normalize(islandValue,0,255));
        }
        else if(islandValue>115 && islandValue<=125)
        {
            biome[i][j]=4;
            return blend(beach,Color.BLACK,normalize(islandValue,0,255));
        }
        biome[i][j]=21;
        return blend(swamp,Color.BLACK,normalize(islandValue,0,255));
    }
    public int[][] generateBiome(double[][] island, double[][] temperature, double[][] humidity,double[][] river)
    {
        image = new BufferedImage(island[0].length,island[0].length, BufferedImage.TYPE_INT_RGB);
        for(int k=0 ; k<island.length ; k++)
        {
            for(int j=0 ; j<island[0].length; j++)
            {
                int islandValue=(int)island[j][k];
                int humidityValue=(int)humidity[j][k];
                int temperatureValue=(int)temperature[j][k];
                int riverValue=(int)river[j][k];
                if(islandValue>150)
                {
                    Color newColor1 = makeDeepWater(j,k,temperatureValue,islandValue);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(islandValue>125)
                {
                    Color newColor1 = makeShallowWater(riverValue,j,k,temperatureValue,islandValue);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(temperatureValue<=25 && humidityValue>75)
                {
                    Color newColor1 = makeIceSpikes(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(temperatureValue<=25 && humidityValue<=50)
                {
                    Color newColor1 = makeTundra(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if((temperatureValue>25 && temperatureValue<=50 && humidityValue>25 &&humidityValue<=75) || (temperatureValue<=25 && humidityValue>50 && humidityValue<=75))
                {
                    Color newColor1 = makeTaiga(islandValue,temperatureValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(temperatureValue>66 && humidityValue<=25)
                {
                    Color newColor1 = makeDesert(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(temperatureValue>25 && temperatureValue<=66 && humidityValue<=25)
                {
                    Color newColor1 = makeBadlands(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if((temperatureValue>66 && humidityValue>75) || (humidityValue>50 && temperatureValue>50 && temperatureValue<=66))
                {
                    Color newColor1 = makeJungle(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(temperatureValue>66 && humidityValue<=75 && humidityValue>50)
                {
                    Color newColor1 = makeForest(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(temperatureValue>75 && humidityValue<=50 && humidityValue>25)
                {
                    Color newColor1 = makeSavanna(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(temperatureValue>50 && temperatureValue<=75 && humidityValue>25 && humidityValue<=50)
                {
                    Color newColor1 = makePlain(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                /*else if((temperatureValue>25 && temperatureValue<=50 && humidityValue>75) || (temperatureValue>50 && temperatureValue<=66 && humidityValue>50 && humidityValue<=75))
                {
                    Color newColor1 = makeJungle(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }*/
                else
                {
                    Color newColor1 = makeSwamp(islandValue,riverValue,j,k);
                    image.setRGB(j,k,newColor1.getRGB());
                }
                /*
                else if(islandValue>30 && islandValue<=50)
                {
                    Color newColor1 = mountain;
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(islandValue<=30)
                {
                    Color newColor1 = snow;
                    image.setRGB(j,k,newColor1.getRGB());
                }

                else if(riverValue>248)
                {
                    Color newColor1 = deepWater;
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(riverValue>245)
                {
                    Color newColor1 = shallowWater;
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(islandValue>125)
                {
                    Color newColor1 = shallowWater;
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else if(islandValue>115)
                {
                    Color newColor1 = beach;
                    image.setRGB(j,k,newColor1.getRGB());
                }
                else
                {
                    if(temperatureValue>66 && humidityValue<25)
                    {
                    Color newColor1 = desert;
                    image.setRGB(j,k,newColor1.getRGB());
                    }
                    else if(humidityValue<=25 && temperatureValue>25 && temperatureValue<=66)
                    {
                    Color newColor1 = badlands;
                    image.setRGB(j,k,newColor1.getRGB());
                    }
                    if(humidityValue>75)
                    {
                        Color newColor1 = rainforest;
                        image.setRGB(j,k,newColor1.getRGB());
                    }
                    else if(humidityValue>25 && humidityValue<=50 && temperatureValue>50)
                    {
                        Color newColor1 = plain;
                        image.setRGB(j,k,newColor1.getRGB());
                    }
                    else if(humidityValue>50 && humidityValue<=75 && temperatureValue>50)
                    {
                        Color newColor1 = forest;
                        image.setRGB(j,k,newColor1.getRGB());
                    }
                    else if(temperatureValue<25)
                    {
                        Color newColor1 = snow;
                        image.setRGB(j,k,newColor1.getRGB());
                    }
                    else
                    {
                        Color newColor1;
                        newColor1 = new Color(35, 76, 0);
                        image.setRGB(j,k,newColor1.getRGB());
                    }
                }*/

            }
        }
        return biome;
    }
}
