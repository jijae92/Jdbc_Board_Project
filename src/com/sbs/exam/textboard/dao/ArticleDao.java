package com.sbs.exam.textboard.dao;

import com.sbs.exam.textboard.Container;
import com.sbs.exam.textboard.dto.Article;
import com.sbs.exam.textboard.util.DBUtil;
import com.sbs.exam.textboard.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDao {

    public Article getArticleById(int id) {

        SecSql sql = new SecSql();
        sql.append("SELECT A.*");
        sql.append(", M.name As extra__writerName");
        sql.append(" FROM article AS A");
        sql.append(" INNER JOIN member AS M");
        sql.append(" ON A.memberId = M.id");
        sql.append(" WHERE A.id = ?", id);

        Map<String, Object> articleMap = DBUtil.selectRow(Container.conn, sql);

        if(articleMap.isEmpty()){
            return null;
        }
        return new Article(articleMap);
    }


    public int add(int memberId, String title, String body) {
        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW()");
        sql.append(", updateDate = NOW()");
        sql.append(", memberId = ?", memberId);
        sql.append(", title = ?" , title );
        sql.append(", `body` = ?" , body);

        int id = DBUtil.insert(Container.conn, sql);
        return id;
    }

    public boolean articleExists(int id) {
        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) AS cnt");
        sql.append(" FROM article");
        sql.append(" WHERE id = ?", id);

        return DBUtil.selectRowBooleanValue(Container.conn, sql);

    }

    public void delete(int id) {
        SecSql sql = new SecSql();
        sql.append("DELETE FROM article");
        sql.append("WHERE id = ?",id);

        DBUtil.delete(Container.conn, sql);
    }

    public void update(int id, String title, String body) {
        SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append(" SET updateDate = NOW()");
        sql.append(", title = ?" , title );
        sql.append(", `body` = ?" , body);
        sql.append(" WHERE id = ?", id);

        DBUtil.update(Container.conn, sql);
    }

    public List<Article> getAticles() {
        SecSql sql = new SecSql();
        sql.append("SELECT A.*, M.name As extra__writerName");
        sql.append(" FROM article AS A");
        sql.append(" INNER JOIN member AS M");
        sql.append(" ON A.memberId = M.id");
        sql.append(" ORDER BY id DESC;");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(Container.conn, sql);
        List<Article> articles = new ArrayList<>();
        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }

        return articles;
    }

    public void increaseHit(int id) {
        SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append("SET hit = hit +1");
        sql.append("WHRER id = ?", id);

        DBUtil.update(Container.conn, sql);
    }
}
