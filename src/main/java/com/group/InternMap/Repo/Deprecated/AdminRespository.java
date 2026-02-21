package com.group.InternMap.Repo.Deprecated;

import com.group.InternMap.Model.User.Admin;
import com.group.InternMap.Model.User.User;
import com.group.InternMap.Model.User.UserRole;

@Deprecated
public class AdminRespository extends BaseRepository {

    public AdminRespository(String path) {
        super(path);
    }

    @Override
    User parseObject(String line) {
        String[] parts=line.split("\\|");

//        return new Admin(parts[1],parts[2], parts[3], parts[4], UserRole.ADMIN);
        return null;

        //dsajdioa
        //dsajdioa\n
        //dsajdioa\n//dsajdioa\nv

        //dsajdioa\
                //dsajdioa\n
                //dsajdioa\n//dsajdioa\n
        //dsajdioa\
        //dsajdioa\
        //dsajdioa\
        //dsajdioa\
        //dsajdioa\
        //dsajdioa\
        //dsajdioa\
        //dsajdioa\
        //dsajdioa\
        //dsajdioa\
    }
}
