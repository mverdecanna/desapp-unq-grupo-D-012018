import model.Score;
import org.junit.Test;
import org.junit.Assert;

public class ScoreTest {


    @Test
    public void addCommentInScoreTest(){
        String comment = "Buen auto";
        Score score = new Score();
        score.setComment(comment);
        Assert.assertEquals(comment, score.getComment());
    }


    @Test
    public void modifiedComentInScoreTest(){
        String comment = "Buen auto";
        String newComment ="Genial Auto";
        Score score = new Score("1", 22, comment, "22", "2032023168", "20320231999");
        Assert.assertEquals(score.getComment(), comment);
        score.setComment(newComment);
        Assert.assertEquals(score.getComment(), newComment);
    }



    @Test
    public void valueInScoreTest(){
        Score score = new Score("1", 22, "algun comentario", "22", "2032023168", "20320231999");
        Assert.assertTrue(score.getValue().equals(22));
    }


}
