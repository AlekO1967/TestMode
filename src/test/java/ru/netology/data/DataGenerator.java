package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private static RequestSpecification specification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private DataGenerator() {
    }

    public static void requestForm(Registration registration) {
        given()
                .spec(specification)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static Registration generateActiveCustomer() {
        Faker faker = new Faker(new Locale("en"));
        Registration validActiveCustomer = new Registration(faker.name().firstName(), faker.internet().password(),
                "active");
        requestForm(validActiveCustomer);
        return validActiveCustomer;
    }

    public static Registration generateBlockedCustomer() {
        Faker faker = new Faker(new Locale("en"));
        Registration validBlockedCustomer = new Registration(faker.name().firstName(), faker.internet().password(),
                "blocked");
        requestForm(validBlockedCustomer);
        return validBlockedCustomer;
    }

    public static Registration generateInvalidCustomerLogin() {
        Faker faker = new Faker(new Locale("en"));
        Registration invalidLogin = new Registration(faker.name().firstName(), "password", "active");
        requestForm(invalidLogin);
        return new Registration(faker.name().firstName(), "password", "active");

    }

    public static Registration generateInvalidCustomerPassword() {
        Faker faker = new Faker(new Locale("en"));
        Registration invalidPassword = new Registration("login", faker.internet().password(),"active");
        requestForm(invalidPassword);
        return new Registration("login", faker.internet().password(),"active");
    }

  }
