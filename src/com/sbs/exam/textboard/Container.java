package com.sbs.exam.textboard;


import com.sbs.exam.textboard.controller.ArticleController;
import com.sbs.exam.textboard.controller.MemberController;
import com.sbs.exam.textboard.dao.ArticleDao;
import com.sbs.exam.textboard.dao.MemberDao;
import com.sbs.exam.textboard.service.ArticleService;
import com.sbs.exam.textboard.service.MemberService;
import com.sbs.exam.textboard.session.Session;

import java.sql.Connection;
import java.util.Scanner;

public class Container {
    public static ArticleController articleController;
    public static MemberController memberController;

    public static ArticleService articleService;

    public static MemberService memberService;
    public static ArticleDao  articleDao;
    public static MemberDao memberDao;

    public static Session session;
    public static Scanner scanner;
    public static Connection conn;

    public static void init() {
        scanner = new Scanner(System.in);
        session = new Session();

        articleDao = new ArticleDao();
        memberDao = new MemberDao();

        articleService = new ArticleService();
        memberService = new MemberService();

        articleController = new ArticleController();
        memberController = new MemberController();
    }
}
