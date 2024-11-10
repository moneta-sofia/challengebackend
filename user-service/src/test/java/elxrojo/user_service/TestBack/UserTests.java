package elxrojo.user_service.TestBack;

import org.junit.jupiter.api.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class UserTests {

    @Nested
    @Order(1)
    @TestClassOrder(ClassOrderer.OrderAnnotation.class)
    class createUser{

        @Test
        @Order(1)
        public void positive(){

        }


    }


}
