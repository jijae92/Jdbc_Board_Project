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
                int id = articleLastId + 1;
                articleLastId++;
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

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
