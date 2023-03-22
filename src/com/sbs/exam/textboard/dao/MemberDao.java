package com.sbs.exam.textboard.dao;

import com.sbs.exam.textboard.Container;
import com.sbs.exam.textboard.dto.Member;
import com.sbs.exam.textboard.util.DBUtil;
import com.sbs.exam.textboard.util.SecSql;

import java.sql.Connection;
import java.util.Map;

public class MemberDao {

    public boolean isLoginedDup(String loginId) {

        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) > 0");
        sql.append("FROM `member`");
        sql.append("WHERE loginId = ?", loginId);

        return DBUtil.selectRowBooleanValue(Container.conn, sql);
    }

    public int join(String loginId, String loginPw, String name) {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO `member`");
        sql.append("SET regDate = NOW()");
        sql.append(", updateDate = NOW()");
        sql.append(", loginId = ?" , loginId );
        sql.append(", loginPw = ?" , loginPw);
        sql.append(", name = ?" , name);

        int id = DBUtil.insert(Container.conn, sql);
        return id;
    }

    public Member getMemberByLoginId(String loginId) {
        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append("FROM `member`");
        sql.append("WHERE loginId = ? ", loginId);

        Map<String, Object> memberMap = DBUtil.selectRow(Container.conn, sql);

        if(memberMap.isEmpty()) {
            return null;
        }
        return new Member(memberMap);
    }
}
