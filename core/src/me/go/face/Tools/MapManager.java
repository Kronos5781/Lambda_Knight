package me.go.face.Tools;



public class MapManager {

    private static final String map1 = "tiledmaps/map1.tmx";
    private static final String map2 = "tiledmaps/map2.tmx";
    private static final String map3 = "tiledmaps/map3.tmx";

    public MapManager()
    {
    }

    public String getMap(int mapID)
    {
        if(mapID == 1){
            return map1;
        }else if(mapID == 2){
            return map2;
        }else if(mapID == 3)
            return map3;
        else{ return map1;}
    }
}
