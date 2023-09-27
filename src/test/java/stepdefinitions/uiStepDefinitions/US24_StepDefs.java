package stepdefinitions.uiStepDefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import static io.restassured.RestAssured.given;
import io.restassured.response.Response;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import pages.*;
import utilities.ActionUtils;
import utilities.JSUtils;
import utilities.WaitUtils;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static base_url.BaseUrl.spec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class US24_StepDefs {
    TeacherMeetManagementPage teacherMeetManagementPage = new TeacherMeetManagementPage();
    ViceDean_AdminManagementPage viceDeanAdminManagementPage = new ViceDean_AdminManagementPage();

    ViceDean_LessonManagementPage viceDeanLessonManagementPage = new ViceDean_LessonManagementPage();
    CommonLocator commonLocator = new CommonLocator();
    ViceDeanTeacherManagementPage viceDeanTeacherManagementPage = new ViceDeanTeacherManagementPage();
    Faker faker = new Faker();
    public static String fUsername;
    public static String fName;
    public static String fSurname;
    private static String fBirthPlace;
    public static String fEmail;
    public static String formPhoneNumber;
    public static String fSsn;
    public static String gender_DB;
    public static String gender_API;
    Connection connection;
    public Statement statement;
    Response response;
    ResultSet resultSet;


    @Given("click Teacher Management Link")
    public void click_teacher_management_link() {
        JSUtils.clickWithTimeoutByJS(teacherMeetManagementPage.teacherManagementLInk);
    }

    @Then("select is Advisor Teacher")
    public void select_is_Advisor_Teacher() {
        JSUtils.clickWithTimeoutByJS(teacherMeetManagementPage.isAdvisorTeacher);
    }

    @Given("enter Invalid Password")
    public void enter_Invalid_Password() {
        teacherMeetManagementPage.lastTeacherPassword.sendKeys("abcd1234");
        WaitUtils.waitFor(3);

    }


    @Then("verify passwordWithoutUpperCaseErrorMessage")
    public void verify_password_without_upper_case_error_message() {
        WaitUtils.waitForVisibility(commonLocator.passwordWithoutUpperCaseErrorMessage, 5);
        assertTrue(commonLocator.passwordWithoutUpperCaseErrorMessage.isDisplayed());
        WaitUtils.waitFor(3);

    }

    ContactPage contactPage = new ContactPage();

    @Given("enter existing email")
    public void enter_existing_email() {
        contactPage.email.sendKeys("yaseen.beckhem@feerock.com");

    }

    @Then("verify existingEmailMasage")
    public void verify_existing_email_masage() {
        WaitUtils.waitForVisibility(commonLocator.existingEmailMasage, 5);
        assertTrue(commonLocator.existingEmailMasage.isDisplayed());
        WaitUtils.waitFor(3);

    }

    @When("Get Teacher User Via username {string}")
    public void Get_Teacher_User_Via_username(String string) throws SQLException {
        statement = connection.createStatement();
        String sqlQuery = "select * from \"public\".teacher where username='" + fUsername + "'";
        System.out.println(sqlQuery);
        resultSet = statement.executeQuery(sqlQuery);


    }

    @Given("Connect to dataBase")
    public void Connect_to_database() throws SQLException {
        connection = DriverManager.
                getConnection("jdbc:postgresql://managementonschools.com:5432/school_management", "select_user", "43w5ijfso");
    }

    @Given("select lesson")
    public void select_lesson() {
        WaitUtils.waitFor(2);
        ActionUtils.doubleClick(viceDeanTeacherManagementPage.chooseLesson);
        viceDeanTeacherManagementPage.chooseLesson.sendKeys("EmillyLesson", Keys.ENTER);
        WaitUtils.waitFor(3);

    }

    @Given("send Get request to get teacher by getAll")
    public void sendGetRequesttogetteacherbyGetAll() {


        spec.pathParams("first", "teachers", "second", "getSavedTeacherById", "third", 1295);
        response = given(spec).get("{first}/{second}/{third}");

    }

    @Given("send Get request to get teacher by getAll on API")
    public void send_Get_request_to_get_teacher_by_getAll_on_API() {
        https://managementonschools.com/app/teachers/getSavedTeacherById/1295
        spec.pathParams("first", "teachers", "second", "getSavedTeacherById", "third", 1295);
        response = given(spec).get("{first}/{second}/{third}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("teachers.size()", greaterThan(0)).extract().response();
         response.prettyPrint();


        }
    @Then("Validate teacher is created")
    public void Validate_teacher_is_created () {
        response.then().statusCode(200)
                .body("object.username", equalTo("TedBaker01"))
                .body("object.name", equalTo("Ted"))
                .body("object.surname", equalTo("Baker"))
                .body("object.email", equalTo("TedBaker@gmail.com"))
                .body("object.gender", equalTo("MALE"))
                .body("object.birthPlace", equalTo("London"))
                .body("object.phoneNumber", equalTo("963-66-9999"))
                .body("object.ssn", equalTo("315-25-2456"))
                .body("object.birthDay", equalTo("2000-02-21"))
                .body("object.isAdvisor", equalTo(true));
        response.prettyPrint();


    }
}




