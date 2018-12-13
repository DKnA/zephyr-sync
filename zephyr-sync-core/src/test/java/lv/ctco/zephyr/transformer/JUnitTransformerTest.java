package lv.ctco.zephyr.transformer;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class JUnitTransformerTest {
    private JUnitTransformer transformer = new JUnitTransformer();

    @Test
    public void testGetJiraIssueId_existInTestName() throws Exception {
        String testName = "[Marketplace] [Logout] [Consumer] [issueId=OLPDFTS-3285] should be able to logout from marketplace";
        List<String> jiraIssueId = transformer.getJiraIssueId(testName);

        assertThat(jiraIssueId.get(0), is("OLPDFTS-3285"));
    }

    @Test
    public void testGetJiraIssueId_doesNotExistInTestName() throws Exception {
        String testName = "[Marketplace] [Logout] [Consumer] should be able to logout from marketplace";
        List<String> jiraIssueId = transformer.getJiraIssueId(testName);

        assertThat(jiraIssueId.size(), is(0));
    }

    @Test
    public void testGetJiraTestId_existInTestName() throws Exception {
        String testName = "[Marketplace] [Logout] [Consumer] [issueId=OLPDFTS-3285] [testId=OLPDFTS-4444] should be able to logout from marketplace";
        String testId = transformer.getTestId(testName);

        assertThat(testId, is("OLPDFTS-4444"));
    }

    @Test
    public void testGetJiraTestId_doesNotExistInTestName() throws Exception {
        String testName = "[Marketplace] [Logout] [Consumer] should be able to logout from marketplace";
        String testId = transformer.getTestId(testName);

        assertThat(testId, nullValue());
    }

    @Test
    public void testRemoveMetaInfoFromName_existInTestName() throws Exception {
        String testName = "[Marketplace] [Logout] [Consumer] [issueId=OLPDFTS-3285] should be able to logout from marketplace";
        String clearName = transformer.removeMetaInfoFromTestName(testName);

        assertThat(clearName, is("[Marketplace] [Logout] [Consumer] should be able to logout from marketplace"));
    }

    @Test
    public void testRemoveMetaInfoFromName_existInTestName_2() throws Exception {
        String testName = "[Marketplace] [Logout] [Consumer] [issueId=OLPDFTS-3285] [testId=OLPDFTS-4444] should be able to logout from marketplace";
        String clearName = transformer.removeMetaInfoFromTestName(testName);

        assertThat(clearName, is("[Marketplace] [Logout] [Consumer] should be able to logout from marketplace"));
    }

    @Test
    public void testRemoveMetaInfoFromName_existInTestName_3() throws Exception {
        String testName = "[Marketplace] [Logout] [Consumer] [testId=OLPDFTS-4444] should be able to logout from marketplace";
        String clearName = transformer.removeMetaInfoFromTestName(testName);

        assertThat(clearName, is("[Marketplace] [Logout] [Consumer] should be able to logout from marketplace"));
    }

    @Test
    public void testRemoveMetaInfoFromName_doesNotExistInTestName() throws Exception {
        String testName = "[Marketplace] [Logout] [Consumer] should be able to logout from marketplace";
        String clearName = transformer.removeMetaInfoFromTestName(testName);

        assertThat(clearName, is("[Marketplace] [Logout] [Consumer] should be able to logout from marketplace"));
    }

    @Test
    public void testIsValidTestCase_hasBeforeAllEvent() throws Exception {
        String testName = "[Marketplace] [Login] [Consumer] \"before all\" hook for \"[issueId=OLPDFTS-3285] should login to marketplace\"";
        boolean isValid = transformer.isValidTestCase(testName);

        assertThat(isValid, is(false));
    }

    @Test
    public void testIsValidTestCase_hasAfterAllEvent() throws Exception {
        String testName = "[Marketplace] [Login] [Consumer] \"after all\" hook for \"[issueId=OLPDFTS-3285] should login to marketplace\"";
        boolean isValid = transformer.isValidTestCase(testName);

        assertThat(isValid, is(false));
    }


    @Test
    public void testIsValidTestCase_hasBeforeEachEvent() throws Exception {
        String testName = "[Marketplace] [Login] [Provider] \"before each\" hook for \"[issueId=OLPDFTS-3285] should login to marketplace\"";
        boolean isValid = transformer.isValidTestCase(testName);

        assertThat(isValid, is(false));
    }

    @Test
    public void testIsValidTestCase_hasAfterEachEvent() throws Exception {
        String testName = "[Marketplace] [Login] [Provider] \"after each\" hook for \"[issueId=OLPDFTS-3285] should login to marketplace\"";
        boolean isValid = transformer.isValidTestCase(testName);

        assertThat(isValid, is(false));
    }
}
