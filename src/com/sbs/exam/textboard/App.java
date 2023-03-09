package com.sbs.exam.textboard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public void run() {
        Scanner sc = Container.scanner;
        int articleLastId = 0;
        List<Article> articles = new ArrayList<>();

        while (true) {
            System.out.printf("명령어) ");
            String cmd = sc.nextLine();
            if(cmd.equals("article add")){
                System.out.println("== 게시물 생성 ==");

                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                int id = articleLastId + 1;
                articleLastId++;

                Connection conn = null;

                PreparedStatement pstat = null;

                try{
                    Class.forName("com.mysql.jdbc.Driver");

                    String url = "jdbc:mysql://127.0.0.1:3306/text_board?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";

                    conn = DriverManager.getConnection(url, "jijae92", "tiger");

                    String sql = "INSERT INTO article" ;
                    sql +=     " SET regDate = NOW()" ;
                    sql +=       ",updateDate = NOW()" ;
                    sql +=       ",title = \"" + title + "\"" ;
                    sql +=       ",`body` = \"" + body + "\";";

                    pstat = conn.prepareStatement(sql);
                    int affectedRows = pstat.executeUpdate();

                    System.out.println("affectedRows : " + affectedRows);

                    System.out.println(sql);

                }
                catch(ClassNotFoundException e){
                    System.out.println("드라이버 로딩 실패");
                }
                catch(SQLException e){
                    System.out.println("에러: " + e);
                }
                finally{
                    try{
                        if( conn != null && !conn.isClosed()){
                            conn.close();
                        }
                    }
                    catch( SQLException e){
                        e.printStackTrace();
                    }

                    try{
                        if(pstat != null && !pstat.isClosed()){
                            pstat.close();
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                }

                Article article = new Article(id,title,body);
                articles.add(article);

                System.out.println("생성된 게시물 객체 : " + article);
                System.out.printf("%d번 게시물을 생성하였습니다.\n", id);
            }
            else if(cmd.equals("article list")) {
                if(articles.isEmpty()){
                    System.out.println("게시물이 존재하지 않습니다.");
                    continue;
                }

                System.out.println("== 게시물 리스트 ==");


                System.out.println("번호 / 제목");
                for(Article article : articles){
                    System.out.printf("%d / %s\n", article.id, article.title);
                }
            } else if (cmd.equals("system exit")) {
                System.out.println("시스템 종료");
                break;
            }
        }
    }
}
