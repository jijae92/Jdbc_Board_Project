package com.sbs.exam.textboard;

import com.sbs.exam.textboard.util.DBUtil;
import com.sbs.exam.textboard.util.SecSql;

import java.sql.*;
import java.util.*;

public class App {
    public void run() {
        Scanner sc = Container.scanner;


        while (true) {
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();
            cmd = cmd.trim();

            // DB 연걸시작
            Connection conn = null;
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                System.out.println("예외 : MySQL 드라이버 클래스가 없습니다.");
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
            try {
                conn = DriverManager.getConnection(url, "jijae92", "tiger");

                int actionResult = doAction(conn, sc, cmd);
                if(actionResult == -1){
                    break;
                }
            } catch (SQLException e) {
                System.out.println("예외 : DB에 연결할 수 없습니다.");
                System.out.println("프로그램을 종료합니다.");
                break;
            }finally {
                try{
                    if(conn !=  null && !conn.isClosed()){
                        conn.close();
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            // DB 연결 끝
        }
        sc.close();
    }

    private int doAction(Connection conn, Scanner sc, String cmd) {
        if(cmd.equals("article add")){
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

        else if(cmd.startsWith("article modify ")){
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

        else if(cmd.equals("article list")) {

            ResultSet rs = null;
            PreparedStatement pstat = null;

            List<Article> articles = new ArrayList<>();

            SecSql sql = new SecSql();
            sql.append("SELECT *");
            sql.append(" FROM article");
            sql.append(" ORDER BY id DESC;");

            List<Map<String, Object>> articleListMap = DBUtil.selectRows(conn, sql);
            for(Map<String, Object> articleMap : articleListMap){
                articles.add(new Article(articleMap));
            }

            if(articles.isEmpty()){
                System.out.println("게시물이 존재하지 않습니다.");
                return -1;
            }
            System.out.println("== 게시물 리스트 ==");
            System.out.println("번호 / 제목");

            for(Article article : articles){
                System.out.printf("%d / %s\n", article.id, article.title);
            }
        } else if (cmd.equals("system exit")) {
            System.out.println("시스템 종료");
            System.exit(0);
        } else {
            System.out.println("명령어를 확인해주세요.");
        }
        return 0;
    }
}
