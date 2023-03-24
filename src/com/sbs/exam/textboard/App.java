package com.sbs.exam.textboard;

import java.sql.*;
import java.util.*;

public class App {
    public void run() {
        Container.scanner = new Scanner(System.in);

        Container.init();


        while (true) {
            System.out.printf("명령어) ");
            String cmd = Container.scanner.nextLine();
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
                Container.conn = conn;

                int actionResult = action(cmd);
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
        Container.scanner.close();
    }

    private int action(String cmd) {


        if(cmd.equals("member join")){
            Container.memberController.join();
        }
        else if(cmd.equals("member login")){
            Container.memberController.login();
        }
        else if(cmd.equals("member logout")){
            Container.memberController.logout();
        }
        else if(cmd.equals("member whoami")){
            Container.memberController.whoami();
        }
        else if(cmd.equals("article add")) {
            Container.articleController.add(cmd);

        }
        else if(cmd.equals("article list")) {

            Container.articleController.showList(cmd);

        }
        else if(cmd.startsWith("article detail ")){
            Container.articleController.showDetail(cmd);
        }
        else if(cmd.startsWith("article modify ")){
            Container.articleController.modify(cmd);
        }

        else if(cmd.startsWith("article delete ")){
            Container.articleController.delete(cmd);
        }


        else if (cmd.equals("system exit")) {
            System.out.println("시스템 종료");
            System.exit(0);
        } else {
            System.out.println("명령어를 확인해주세요.");
        }
        return 0;
    }
}
