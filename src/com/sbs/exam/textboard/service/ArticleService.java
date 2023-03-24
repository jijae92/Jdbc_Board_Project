package com.sbs.exam.textboard.service;

import com.sbs.exam.textboard.Container;
import com.sbs.exam.textboard.dto.Article;
import com.sbs.exam.textboard.dao.ArticleDao;

import java.util.List;

public class ArticleService {
    private ArticleDao articleDao;
    public ArticleService() {
        articleDao = Container.articleDao;
    }

    public int add(int memberId, String title, String body) {
        return articleDao.add(memberId, title, body);
    }

    public boolean articleExists(int id) {
        return articleDao.articleExists(id);
    }

    public void delete(int id) {
        articleDao.delete(id);
    }

    public Article getArticleById(int id) {
        return articleDao.getArticleById(id);
    }

    public void update(int id, String title, String body) {
        articleDao.update(id, title, body);
    }

    public List<Article> getAticles() {
        return  articleDao.getAticles();
    }
}
