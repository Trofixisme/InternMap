package com.group.InternMap.Deprecated;

import com.group.InternMap.Job.FullTime;

import java.util.List;

@Deprecated
@SuppressWarnings("all")
public class FullTimeRepository extends BaseRepository {
    protected static final String path="data/FullTime.txt";

    public FullTimeRepository() {
        super(path);
    }

    //TODO: FIX
    @Override
    FullTime parseObject(String line) {

        String[] parts = line.split("\\|", -1);  // IMPORTANT: keep empty fields!

        if (parts.length < 8) {
            throw new IllegalArgumentException("Invalid line format (" + parts.length + " fields): " + line);
        }

        //String fullTimeID ,String JobDescription, String JobName, String JobRequirements, String JobTitle, PostingType jobPostingType,String benefits, Company
        //company
//        return new FullTime(
//
//                parts[0],                    // fullTimeID
//                parts[1],                    // JobDescription
//                parts[2],                    // JobName
//                parts[3],                    // JobRequirements
//                parts[4],                    // JobTitle
//                PostingType.valueOf(parts[5]),  // jobPostingType enum
//                parts[6],                    // benefits
//                parseCompany(parts[7]) // Company object
//        );

        return null;
    }

    private static class RepositoryTest {

        static void main(String[] args) {

            BaseRepository repo = new FullTimeRepository();

            List<Object> users = repo.findAll();
            users.forEach(System.out::println);
        }
    }
}