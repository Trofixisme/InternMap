//package com.group.InternMap.Repo.Deprecated;
//
//import com.group.InternMap.Model.Roadmap.Roadmap;
//import com.group.InternMap.Model.Roadmap.RoadmapModule;
//import java.util.Arrays;
//
//@Deprecated
//@SuppressWarnings("all")
//public class RoadmapRepository extends BaseRepository {
//
//    public RoadmapRepository(String path) {
//        super("data/roadmap.txt");
//    }
//
//    public void save(Roadmap roadmap) {
//
//        writeToFile(path, roadmap.toString(),true);
//    }
//
//    //TODO: Modify this method to include the UUID
//
//    @Override
//    Object parseObject(String line) {
//        String[] parts = line.split("\\|", -1);
//        return new Roadmap(parts[0], parts[1], (RoadmapModule) Arrays.asList(parts[4].split(",")));
//    }
//}
