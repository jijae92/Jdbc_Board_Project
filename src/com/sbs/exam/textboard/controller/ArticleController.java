package com.sbs.exam.textboard.controller;

import com.sbs.exam.textboard.Article;
import com.sbs.exam.textboard.util.DBUtil;
import com.sbs.exam.textboard.util.SecSql;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleController extends Controller{
    public void add(String cmd) {

        System.out.println("== 게시물 생성 ==");

        System.out.printf("제목 : ");
        String title = sc.nextLine();
        System.out.printf("내용 : ");
        String body = sc.nextLine();

        SecSql sql = new SecSql();
        sql.append("INSERT INTO article");
        sql.append("SET regDate = NOW()");
        sql.append(", updateDate = NOW()");
        sql.append(", title = ?" , title );
        sql.append(", `body` = ?" , body);

        int id = DBUtil.insert(conn, sql);
        System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
    }

    public void showList(String cmd) {
        List<Article> articles = new ArrayList<>();

        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append(" FROM article");
        sql.append(" ORDER BY id DESC;");

        List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
        for (Map<String, Object> articleMap : articleListMap) {
            articles.add(new Article(articleMap));
        }

        if (articles.isEmpty()) {
            System.out.println("게시물이 존재하지 않습니다.");
            return;
        }
        System.out.println("== 게시물 리스트 ==");
        System.out.println("번호 / 제목");

        for (Article article : articles) {
            System.out.printf("%d / %s\n", article.id, article.title);
        }
    }

    public void showDetail(String cmd) {
        int id = Integer.parseInt(cmd.split(" ")[2]);

        SecSql sql = new SecSql();
        sql.append("SELECT *");
        sql.append(" FROM article");
        sql.append(" WHERE id = ?", id);

        Map<String, Object> articleMap = DBUtil.selectRow(conn, sql);

        if(articleMap.isEmpty()){
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
            return;
        }
        Article article = new Article(articleMap);
        System.out.printf("=== %d번 게시물 상세본기 ===\n", article.id);
        System.out.printf("현재날짜 : %s\n", article.regDate);
        System.out.printf("수정날짜 : %s\n", article.updateDate);
        System.out.printf("제목 : %s\n", article.title);
        System.out.printf("내용 : %s\n", article.body);

    }

    public void modify(String cmd) {
        int id = Integer.parseInt(cmd.split(" ")[2]);

        System.out.printf("== %d번 게시물 수정 ==\n", id);

        System.out.printf("새 제목 : ");
        String title = sc.nextLine();
        System.out.printf("새 내용 : ");
        String body = sc.nextLine();

        SecSql sql = new SecSql();
        sql.append("UPDATE article");
        sql.append(" SET updateDate = NOW()");
        sql.append(", title = ?" , title );
        sql.append(", `body` = ?" , body);
        sql.append(" WHERE id = ?", id);

        DBUtil.update(conn, sql);

        System.out.printf("%d번 게시글이 수정되었습니다.\n", id);

    }
    public void delete(String cmd) {
        int id = Integer.parseInt(cmd.split(" ")[2]);

        System.out.printf("== %d번 게시물 삭제 ==\n", id);

        SecSql sql = new SecSql();
        sql.append("SELECT COUNT(*) AS cnt");
        sql.append(" FROM article");
        sql.append(" WHERE id = ?", id);

        int articleCount = DBUtil.selectRowIntValue(conn, sql);
        if(articleCount == 0) {
            System.out.printf("%d번 게시글은 존재하지 않습니다.\n", id);
            return;
        }

        sql = new SecSql();
        sql.append("DELETE FROM article");
        sql.append("WHERE id = ?",id);

        DBUtil.delete(conn, sql);

        System.out.printf("%d번 게시글이 삭제되었습니다.\n", id);

    }
}
