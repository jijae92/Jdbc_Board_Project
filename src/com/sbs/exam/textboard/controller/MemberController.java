package com.sbs.exam.textboard.controller;

import com.sbs.exam.textboard.Container;
import com.sbs.exam.textboard.dto.Member;
import com.sbs.exam.textboard.service.MemberService;

public class MemberController extends Controller{

    private MemberService memberService;

    public MemberController(){
        memberService= Container.memberService;

    }


    public void join() {
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

    public void login() {
        String loginId;
        String loginPw;

        System.out.println("== 로그인 ==");

        while(true) {
            System.out.printf("로그인 아이디 : ");
            loginId = sc.nextLine().trim();

            if (loginId.length() == 0) {
                System.out.println("로그인 아이디를 입력해주세요.");
                continue;
            }

            boolean isLoginedDup = memberService.isLoginedDup(loginId);
            if (isLoginedDup == false){
                System.out.printf("%s는 존재하지 않는 로그인 아이디입니다.\n", loginId);
                continue;
            }

            break;
        }

        Member member = memberService.getMemberByLoginId(loginId);

        int loginPwTryMaxCount = 3;
        int loginPwTryCount = 0;

        while(true) {
            if(loginPwTryCount >= loginPwTryMaxCount){
                System.out.println("비밀번호를 확인 후 다음에 다시 시도해주세요.");
                break;
            }

            System.out.printf("로그인 비밀번호 : ");
            loginPw = sc.nextLine().trim();

            if (loginPw.length() == 0) {
                loginPwTryCount++;
                System.out.println("로그인 비밀번호를 입력해주세요.");
                continue;
            }
            if (member.loginPw.equals(loginPw) == false){
                loginPwTryCount++;
                System.out.println("비밀번호가 일치하지 않습니다.");
                continue;
            }
            System.out.printf("%s님 환영합니다.\n", member.name);
            Container.session.login(member);
            break;
        }

    }

    public void whoami() {
        if(Container.session.isLogined() == false){
            System.out.println("로그인 상태가 아닙니다.");
        }
        else {
            System.out.println(Container.session.loginedMember.loginId);
        }
    }

    public void logout() {
        if(Container.session.isLogined() == false){
            System.out.println("이미 로그아웃 상태입니다.");
            return;
        }
        else {
            Container.session.logout();
            System.out.println("로그아웃 되었습니다.");
        }
    }

}
