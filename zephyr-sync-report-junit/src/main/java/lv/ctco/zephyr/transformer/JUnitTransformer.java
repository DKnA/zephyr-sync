package lv.ctco.zephyr.transformer;

import lv.ctco.zephyr.ZephyrSyncException;
import lv.ctco.zephyr.beans.TestCase;
import lv.ctco.zephyr.beans.testresult.junit.JUnitResult;
import lv.ctco.zephyr.beans.testresult.junit.JUnitResultTestSuite;
import lv.ctco.zephyr.beans.testresult.junit.JUnitResultTestSuites;
import lv.ctco.zephyr.enums.TestStatus;
import lv.ctco.zephyr.util.Utils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JUnitTransformer implements ReportTransformer {

    public String getType() {
        return "junit";
    }

    public List<TestCase> transformToTestCases(String reportPath) {
        List<TestCase> result = new ArrayList<TestCase>();

        for (JUnitResultTestSuite jUnitResultTestSuite : readJUnitReport(reportPath).getTestsuite()) {
            result.addAll(transform(jUnitResultTestSuite));
        }

        return result;
    }

    JUnitResultTestSuites readJUnitReport(String path) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(JUnitResultTestSuites.class);
            return (JUnitResultTestSuites) jaxbContext.createUnmarshaller().unmarshal(new File(path));
        } catch (JAXBException e) {
            throw new ZephyrSyncException("Cannot process JUnit report", e);
        }
    }

    List<TestCase> transform(JUnitResultTestSuite resultTestSuite) {
        if (resultTestSuite.getTestcase() == null) {
            return new ArrayList<TestCase>();
        }

        List<TestCase> result = new ArrayList<TestCase>();
        for (JUnitResult testCase : resultTestSuite.getTestcase()) {
            String originalName = testCase.getName();
            if (!isValidTestCase(originalName)) {
                continue;
            }

            TestCase test = new TestCase();
            String name = removeMetaInfoFromTestName(originalName);

            test.setName(name);

            test.setKey(getTestId(originalName));
            test.setDescription("Description: " + name);
            test.setUniqueId(generateUniqueId(testCase));
            test.setStatus(testCase.getError() != null || testCase.getFailure() != null ? TestStatus.FAILED : TestStatus.PASSED);
            test.setStoryKeys(getJiraIssueId(originalName));

            result.add(test);
        }
        return result;
    }

    String generateUniqueId(JUnitResult testCase) {
        return String.join("-", Utils.normalizeKey(testCase.getClassname()), Utils.normalizeKey(testCase.getName()));
    }

    List<String> getJiraIssueId(String text) {
        List<String> jiraIssueId = new ArrayList<>();
        IssuePair indexes = getIssueIdIndexes(text);
        int startOf = indexes.startOf;
        int endOf = indexes.endOf;

        if (startOf >= 0 && endOf > 0) {
            jiraIssueId.add(text.substring(startOf + 9, endOf));
        }

        return jiraIssueId;
    }

    String getTestId(String text) {
        String testId = null;
        IssuePair indexes = getTestIdIndexes(text);
        int startOf = indexes.startOf;
        int endOf = indexes.endOf;

        if (startOf >= 0 && endOf > 0) {
            testId = text.substring(startOf + 8, endOf);
        }

        return testId;
    }

    private IssuePair getTestIdIndexes(String text) {
        return getIssueIdIndexesOf(text, "testId");
    }

    private IssuePair getIssueIdIndexes(String text) {
        return getIssueIdIndexesOf(text, "issueId");
    }

    private IssuePair getIssueIdIndexesOf(String text, String match) {
        int startOf = text.indexOf("[" + match + "=");
        int endOf = text.indexOf("]", startOf);

        return new IssuePair(startOf, endOf);
    }

    String removeMetaInfoFromTestName(String text) {
        String subtext = removeIssueIdFromTestName(text);
        return removeTestIdFromTestName(subtext);
    }

    String removeTestIdFromTestName(String text) {
        return removeIdsFromTestName(text, getTestIdIndexes(text));
    }

    String removeIssueIdFromTestName(String text) {
        return removeIdsFromTestName(text, getIssueIdIndexes(text));
    }

    String removeIdsFromTestName(String text, IssuePair indexes) {
        int startOf = indexes.startOf;
        int endOf = indexes.endOf;

        if (startOf >= 0 && endOf > 0) {
            return text.substring(0, startOf) + text.substring(endOf + 2, text.length());
        }

        return text;
    }

    boolean isValidTestCase(String text) {
        return !(text.contains("after each\" hook for") || text.contains("before each\" hook for")
                || text.contains("after all\" hook for") || text.contains("before all\" hook for"));
    }

    static class IssuePair {
        private final int startOf;
        private final int endOf;

        IssuePair(int startOf, int endOf) {
            this.startOf = startOf;
            this.endOf = endOf;
        }
    }
}