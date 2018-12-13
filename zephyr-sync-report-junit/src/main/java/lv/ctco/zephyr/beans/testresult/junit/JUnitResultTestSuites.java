
package lv.ctco.zephyr.beans.testresult.junit;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "testsuites", propOrder = {
        "properties",
        "testsuite"
})
@XmlRootElement(name = "testsuites")
public class JUnitResultTestSuites {

    @XmlElement(required = true)
    protected JUnitTCProperties properties;
    protected List<JUnitResultTestSuite> testsuite;

    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String name;

    @XmlAttribute(name = "tests", required = true)
    protected int tests;
    @XmlAttribute(name = "failures")
    protected int failures;
    @XmlAttribute(name = "errors")
    protected int errors;
    @XmlAttribute(name = "time", required = true)
    protected BigDecimal time;

    public JUnitTCProperties getProperties() {
        return properties;
    }

    public void setProperties(JUnitTCProperties value) {
        this.properties = value;
    }

    public List<JUnitResultTestSuite> getTestsuite() {
        if (testsuite == null) {
            testsuite = new ArrayList<JUnitResultTestSuite>();
        }
        return this.testsuite;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int value) {
        this.tests = value;
    }

    public int getFailures() {
        return failures;
    }

    public void setFailures(int value) {
        this.failures = value;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int value) {
        this.errors = value;
    }

    public BigDecimal getTime() {
        return time;
    }

    public void setTime(BigDecimal value) {
        this.time = value;
    }
}