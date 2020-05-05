package paralleltest.features.search;

import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.questions.page.TheWebPage;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;
import net.thucydides.junit.annotations.TestData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import paralleltest.tasks.OpenTheApplication;
import paralleltest.tasks.Search;

import java.util.Arrays;
import java.util.Collection;

import static java.util.Arrays.asList;
import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.EventualConsequence.eventually;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;

@RunWith(SerenityParameterizedRunner.class)
public class SearchByKeywordStory {

    Actor anna = Actor.named("Anna");

    @Managed(uniqueSession = true)
    public WebDriver herBrowser;

    @Steps
    OpenTheApplication openTheApplication;

    @Before
    public void annaCanBrowseTheWeb() {
        anna.can(BrowseTheWeb.with(herBrowser));
    }

    @Test
    public void search_results_should_show_the_search_term_in_the_title() {

        givenThat(anna).wasAbleTo(openTheApplication);

        when(anna).attemptsTo(Search.forTheTerm(this.keyword));

        then(anna).should(eventually(seeThat(TheWebPage.title(), containsString(this.expectedResult))));

    }

    @TestData
    public static Collection<Object[]> testData()
    {
        return Arrays.asList(new Object[][]{
                {"BDD In Action", "BDD In Action"},
                {"Testmaster.vn", "Testmaster.vn"},
                {"Vietcombank", "Vietcombank"}
        });
    }

    private final String keyword;
    private final String expectedResult;
    public SearchByKeywordStory(String keyword, String expectedResult)
    {
        this.keyword = keyword;
        this.expectedResult = expectedResult;
    }
}