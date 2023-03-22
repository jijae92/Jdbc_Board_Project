package com.sbs.exam.textboard.dto;

import java.util.Map;

public class Member {
    public int id;
    public String regDate;
    public String updateDate;
    public String loginId;
    public String loginPw;
    public String name;

    public Member(Map<String, Object> memberMap) {
        this.id = (int) memberMap.get("id");
        this.regDate = (String) memberMap.get("regDate");
        this.updateDate = (String) memberMap.get("updateDate");
        this.loginId = (String) memberMap.get("title");
        this.loginPw = (String) memberMap.get("body");
        this.name = (String) memberMap.get("name");

    }
}
