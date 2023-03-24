package com.sbs.exam.textboard.session;

import com.sbs.exam.textboard.dto.Member;

public class Session {
    public int loginedMemberId;
    public Member loginedMember;

    public Session(){
        loginedMemberId = -1;
    }

//    public boolean isLogined() {
//        return loginedMemberId;
//    }
}
