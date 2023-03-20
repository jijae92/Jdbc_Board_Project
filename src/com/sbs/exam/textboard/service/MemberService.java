package com.sbs.exam.textboard.service;

import com.sbs.exam.textboard.dao.MemberDao;
import com.sbs.exam.textboard.util.DBUtil;
import com.sbs.exam.textboard.util.SecSql;

import java.sql.Connection;

public class MemberService {
    private MemberDao memberDao;
    public MemberService(Connection conn) {
        memberDao = new MemberDao(conn);
    }

    public boolean isLoginedDup(String loginId) {
        return memberDao.isLoginedDup(loginId);

    }

    public int join(String loginId, String loginPw, String name) {
        return  memberDao.join(loginId,loginPw,name);
    }
}
