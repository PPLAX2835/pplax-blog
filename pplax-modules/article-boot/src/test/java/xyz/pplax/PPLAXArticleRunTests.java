package xyz.pplax;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import xyz.pplax.article.service.TalkService;

@SpringBootTest
public class PPLAXArticleRunTests {

    @Autowired
    TalkService talkService;
    @Test
    public void TalkServiceTest() {
        talkService.queryTalkByUid(1L);
    }

}
