package com.group.InternMap.Deprecated;

@Deprecated
@SuppressWarnings("all")
public class SkillRepository extends BaseRepository {

    public SkillRepository() {
        super("data/Skill.txt");
    }

    //TODO: FIX
    @Override
    Object parseObject(String line) {
        String[] parts = line.split("\\|");
//        return new Skill(parts[0], parts[1], Arrays.asList(parts[2].split(",")));
        return null;
    }
}