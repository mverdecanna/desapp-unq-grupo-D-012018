import model.CurrentAccount;
import model.builder.CurrentAccountBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by mariano on 01/04/18.
 */
public class CurrentAccountTest {


    @Test
    public void chargeTenCreditsTest(){
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId(11).build();
        Integer tenCredits = 10;
        currentAccount.addCredit(tenCredits);
        Assert.assertTrue(currentAccount.getCredits() == tenCredits);
    }


    @Test
    public void substractFiveCreditsToTenCreditsTest(){
        CurrentAccount currentAccount = new CurrentAccountBuilder().setId(11).build();
        Integer tenCredits = 10;
        Integer fiveCredits = 5;
        currentAccount.addCredit(tenCredits);
        currentAccount.subtractCredit(fiveCredits);
        Assert.assertTrue(currentAccount.getCredits() == fiveCredits);
    }


}
