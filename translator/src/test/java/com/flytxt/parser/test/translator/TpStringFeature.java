package com.flytxt.parser.test.translator;
import static org.junit.Assert.assertEquals;

import com.flytxt.parser.marker.Marker;
import com.flytxt.parser.marker.MarkerFactory;
import com.flytxt.parser.test.translator.transformer.MarkerHelper;
import com.flytxt.parser.test.translator.transformer.MarkerTransform;
import com.flytxt.parser.test.translator.transformer.StringHelper;
import com.flytxt.parser.test.translator.transformer.StringTransform;
import com.flytxt.parser.translator.TpString;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TpStringFeature {
	MarkerFactory mf;
	Marker marker1;
	Marker marker2;
	boolean result;
	String resultStr;
	TpString tpString;

	private Marker getMarker(String str) {
		Marker mocker = new Marker();
		mocker.index = 0;
		mocker.length = str.length();
		return mocker;
	}
	@Given("^marker and tpstring class$")
	public void markerAndTpstringClass() throws Throwable {
		mf = new MarkerFactory();
		tpString = new TpString();
	}

	@When("^\"([^\"]*)\" provided$")
	public void provided(@Transform(MarkerTransform.class) MarkerHelper mh) throws Throwable {
		resultStr = ""+tpString.length(mh.getBytes(), mh.getMarker(), mh.getMf());
	}

	@Then("^return length \"([^\"]*)\"$")
	public void returnLength(String arg1) throws Throwable {
		assertEquals(arg1, resultStr);
	}

	@When("^\"([^\"]*)\" starts with prefix \"([^\"]*)\"$")
	public void startsWithPrefix(String arg1, String arg2) throws Throwable {
		result = tpString.startsWtih(arg1.getBytes(), getMarker(arg1), arg2.getBytes(), getMarker(arg2));
	}

	@Then("^return true if string starts with prefix\"([^\"]*)\"$")
	public void returnTrueIfStringStartsWithPrefix(String arg1) throws Throwable {
		assertEquals(arg1.equals("Y"), result);
	}

	@When("^convert to upperCase\"([^\"]*)\"$")
	public void convertToUpperCase(String arg1) throws Throwable {
		resultStr = tpString.toUpperCase(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
	}

	@Then("^returns upperCase \"([^\"]*)\"$")
	public void returnsUpperCase(String arg1) throws Throwable {
		assertEquals(arg1.equals("Y"), result);
	}

	@When("^convert to lowerCase \"([^\"]*)\"$")
	public void convertToLowerCase(String arg1) throws Throwable {
		resultStr = tpString.toLowerCase(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
	}

	@Then("^returns lowerCase \"([^\"]*)\"$")
	public void returnsLowerCase(String arg1) throws Throwable {
		assertEquals(arg1.equals("Y"), result);
	}

	@When("^conver to titleCase\"([^\"]*)\"$")
	public void converToTitleCase(String arg1) throws Throwable {
		resultStr = tpString.toTitleCase(arg1.getBytes(), getMarker(arg1), mf).toString(arg1.getBytes());
	}

	@Then("^returns titleCase \"([^\"]*)\"$")
	public void returnsTitleCase(String arg1) throws Throwable {
		assertEquals(arg1.equals("Y"), result);
	}

	@When("^\"([^\"]*)\" with leading whitespace is provided$")
	public void withLeadingWhitespaceIsProvided(@Transform(MarkerTransform.class) MarkerHelper mh) throws Throwable {
		resultStr = tpString.lTrim(mh.getBytes(), mh.getMarker(), mh.getMf()).toString(mh.getBytes());
	}

	@Then("^returns string without leading whitespaces \"([^\"]*)\"$")
	public void returnsStringWithoutLeadingWhitespaces(@Transform(StringTransform.class)StringHelper arg1) throws Throwable {
		assertEquals(arg1.getStr(), resultStr);
	}

	@When("^\"([^\"]*)\" with trailing whitespaces provided$")
	public void withTrailingWhitespacesProvided(@Transform(MarkerTransform.class) MarkerHelper mh) throws Throwable {
		resultStr = tpString.rTrim(mh.getBytes(), mh.getMarker(), mh.getMf()).toString(mh.getBytes());
	}

	@Then("^return string without trailing whilespaces \"([^\"]*)\"$")
	public void returnStringWithoutTrailingWhilespaces(@Transform(StringTransform.class)StringHelper arg1) throws Throwable {
		assertEquals(arg1.getStr(), resultStr);
	}

	@When("^\"([^\"]*)\" with whitespace in beginning or end is provided$")
	public void withWhitespaceInBeginningOrEndIsProvided(@Transform(MarkerTransform.class) MarkerHelper mh) throws Throwable {
		resultStr = tpString.trim(mh.getBytes(), mh.getMarker(), mh.getMf()).toString(mh.getBytes());
	}

	@Then("^return string with no whitespaces at start or end \"([^\"]*)\"$")
	public void returnStringWithNoWhitespacesAtStartOrEnd(@Transform(StringTransform.class)StringHelper arg1) throws Throwable {
		assertEquals(arg1.getStr(), resultStr);
	}

	@When("^\"([^\"]*)\" contains \"([^\"]*)\"$")
	public void contains(String arg1, String arg2) throws Throwable {
		result = tpString.contains(arg1.getBytes(), getMarker(arg1),
				arg2.getBytes(), getMarker(arg2),mf);
	}

	@Then("^return true if string contais subString\"([^\"]*)\"$")
	public void returnTrueIfStringContaisSubString(String arg1) throws Throwable {
		assertEquals(arg1.equals("Y"), result);
	}

	@When("^\"([^\"]*)\" contains \"([^\"]*)\" ignore case$")
	public void containsIgnoreCase(String arg1, String arg2) throws Throwable {
		result = tpString.containsIgnoreCase(arg1.getBytes(), getMarker(arg1),
				arg2.getBytes(), getMarker(arg2),mf);
	}

	@Then("^return true if string contais subString ignoreCase \"([^\"]*)\"$")
	public void returnTrueIfStringContaisSubStringIgnoreCase(String arg1) throws Throwable {
		assertEquals(arg1.equals("Y"), result);
	}

}