package com.sbs.exam.textboard.controller;

import com.sbs.exam.textboard.service.MemberService;
import com.sbs.exam.textboard.util.DBUtil;
import com.sbs.exam.textboard.util.SecSql;

import java.sql.Connection;
import java.util.Scanner;

public class MemberController extends Controller{

    private MemberService memberService;

    public MemberController(Connection conn, Scanner sc){
        super(sc);
        memberService= new MemberService(conn);

    }


    public void join(String cmd) {
            String loginId;
            String loginPw;
            String loginPwConfirm;
            String name;

            System.out.println("== 회원 가입 ==");

            while(true) {
                System.out.printf("로그인 아이디 : ");
                loginId = sc.nextLine().trim();

                if (loginId.length() == 0) {
                    System.out.println("로그인 아이디를 입력해주세요.");
                    continue;
                }

                boolean isLoginedDup = memberService.isLoginedDup(loginId);
                if (isLoginedDup){
                    System.out.printf("%s는 이미 사용중인 로그인 아이디입니다.\n", loginId);
                    continue;
                }



                break;
            }
            while(true){
                System.out.printf("로그인 비밀번호 : ");
                loginPw = sc.nextLine().trim();

                if(loginPw.length() == 0){
                    System.out.println("로그인 비밀번호를 입력해주세요.");
                    continue;
                }

                boolean loginPwConfirmIsSame = true;

                while (true){
                    System.out.printf("로그인 비밀번호 확인 : ");
                    loginPwConfirm = sc.nextLine().trim();

                    if (loginPwConfirm.length() == 0 ){
                        System.out.println("로그인 비밀번호 확인을 입력해주세요.");
                    }

                    if(loginPw.equals(loginPwConfirm) == false){
                        System.out.println("로그인 비밀번호가 일치하지 않습니다. 다시 입력해주세요.");
                        loginPwConfirmIsSame = false;
                        break;
                    }
                    break;
                }

                if(loginPwConfirmIsSame){
                    break;
                }

            }
            while(true) {
                System.out.printf("이름 : ");
                name = sc.nextLine().trim();

                if (name.length() == 0) {
                    System.out.println("이름을 입력해주세요.");
                    continue;
                }
                break;
            }

            int id = memberService.join(loginId, loginPw, name);


            System.out.printf("%d번 회원이 생성되었습니다.\n", id);

    }

}
