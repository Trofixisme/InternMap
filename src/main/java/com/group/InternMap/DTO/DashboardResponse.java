package com.group.InternMap.DTO;

import com.group.InternMap.Roadmap.Roadmap;
import com.group.InternMap.User.Users;

import java.util.List;

public class DashboardResponse {
    private List<Users> users;
    private List<Roadmap> roadmaps;

    public DashboardResponse(List<Users> users, List<Roadmap> roadmaps) {
        this.users = users;
        this.roadmaps = roadmaps;
    }

    public List<Users> getUsers() {
        return users;
    }

    public List<Roadmap> getRoadmaps() {
        return roadmaps;
    }
}