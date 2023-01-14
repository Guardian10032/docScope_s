package netRelated;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class netActionTest {

    @Test
    void getName() {
        System.out.println(netAction.getName("alpha"));
    }

    @Test
    void sendEmail() {
        netAction.SendEmail("alpha",0);
    }
}