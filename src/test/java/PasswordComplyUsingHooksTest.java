import static org.junit.jupiter.apiAssertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;

import org.example.junit.test.PasswordComply;
import org.junit.jupiter.api.*;

public class PasswordComplyUsingHooksTest {
    PasswordComply password;
    static File myFileReader;
    static Scanner myScannerReader;
    static InputStream passwordFile;
    TestInfo testInfo;
    TestReporter testReporter;

    @BeforeAll
    private static void prepareLoadTestData() {
        try {
            myFileReader = new File("C:\\JUnit\\PasswordFile.txt");
            myScannerRead = new Scanner(myFileReader);
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    @BeforeEach
    void init(TestInfo testInfo, TestReport testReport) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;
        testReporter = testReporter;
        testReporter.publishEntry("Running" + testInfo.getDisplayName() + " under tags " + testInfo.getTags());
        password = new PasswordComply("");

    }

    @AfterEach
    void cleanUpAfterTest() {
        myScannerReader.close();
        System.out.println("Clean Up of all local resoures...");
    }

    @AfterAll
    static void cleanAll() {
        Runtime.getRuntime().gc();
        System.out.println("Clean up of Application Level DB data, Logs, resources etc.. done");
    }

    @Nested
    @Tag("Configuration-File-Check")
    @DisplayName("Check All Config Files")
    class COnfigFilesAvailable {
        @Test
        @Display("Config")
        void checkALLCOnfigFiles() {
            System.out.println("All Config files created...");
        }

        @Test
        @DisplayName("Config Entries Created...")
        @RepeatedTest(2)
        void checkAllConfigEntries() {
            System.out.println("All Config entries valid...");
        }
    }

    @Test
    @Tag("DB-Password-Checks")
    @DisplayName("Check ALL DB Password are Valid...")
    void testDoesPasswordComply() {
        assumeTrue((myFileReader != null));
        boolean expectedResult = true;
        while (myScannerReader.hasNextLine()) {
            String passwordRead = myScannerRead.nextLine();
            System.out.println("Password Read from File: " + passwordRead);
            password.setPassword(passwordRead);
            boolean actualResult = password.doesPasswordComply();
            assertAll(
                    () -> assertEquals(expectedResult, actualResult, "Password conditions failed!")
            );
        }
    }

    @Disabled
    @Test
    void cleanUpFiles() {
        myFileReader.delete(){
            System.out.println("Deleting all config, password file created.");
        }
    }
}

