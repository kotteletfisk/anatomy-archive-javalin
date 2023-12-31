package dat.controller;

import dat.config.HibernateConfig;
import dat.dto.EquipmentDTO;
import dat.dto.ExerciseDTO;
import dat.dto.ExerciseTypeDTO;
import dat.dto.MuscleDTO;
import dat.entities.Exercise;
import dat.util.Populate;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class ExerciseControllerTest
{
    private static final String BASE_URL = "http://localhost:7070/";
    @BeforeEach
    void setUp()
    {
        HibernateConfig.setTest(true); // ALSO SET IN APPLICATIONCONFIG
        Populate.main(null);
    }

    @Test
    void getAllExercises()
    {
        List<ExerciseDTO> dtos = given()
                .when()
                .get(BASE_URL + "exercise")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("", ExerciseDTO.class);
        assertThat(dtos.size(), equalTo(6));
    }

    @Test
    void getExerciseById()
    {
        ExerciseDTO dto = given()
                .when()
                .get(BASE_URL + "exercise/1")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(ExerciseDTO.class);
        assertThat(dto.getName(), equalTo("Squat"));
    }

    @Test
    void createExercise()
    {
        ExerciseDTO dto = new ExerciseDTO("Chin Up", "Description", null, 5);
        ExerciseDTO response = given()
                .contentType("application/json")
                .body(dto)
                .when()
                .post(BASE_URL + "exercise")
                .then()
                .log().all()
                .statusCode(201)
                .extract().body().as(ExerciseDTO.class);
        assertThat(response.getName(), equalTo("Chin Up"));

        // Check that it was added
        List<ExerciseDTO> dtos = given()
                .when()
                .get(BASE_URL + "exercise")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("", ExerciseDTO.class);
        assertThat(dtos.size(), equalTo(7));
    }

    @Test
    void updateExercise()
    {
        ExerciseDTO dto = new ExerciseDTO("Chin Up", "Description", null, 5);
        ExerciseDTO response = given()
                .contentType("application/json")
                .body(dto)
                .when()
                .put(BASE_URL + "exercise/1")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(ExerciseDTO.class);
        assertThat(response.getName(), equalTo("Chin Up"));

        // Check that it was updated
        ExerciseDTO dto2 = given()
                .when()
                .get(BASE_URL + "exercise/1")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().as(ExerciseDTO.class);
        assertThat(dto2.getName(), equalTo("Chin Up"));
    }

    @Test
    void deleteExercise()
    {
        given()
                .when()
                .delete(BASE_URL + "exercise/1")
                .then()
                .log().all()
                .statusCode(204);

        // Check that it was deleted
        given()
                .when()
                .get(BASE_URL + "exercise/1")
                .then()
                .log().all()
                .statusCode(404);
    }

    @Test
    void addMuscleToExercise()
    {
        given()
                .when()
                .post(BASE_URL + "exercise/muscle?exerciseId=2&muscleId=2")
                .then()
                .log().all()
                .statusCode(201);

        // Check that it was added
        List<MuscleDTO> dtos = given()
                .when()
                .get(BASE_URL + "exercise/2/muscle")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("", MuscleDTO.class);

        assertThat(dtos.size(), equalTo(1));
    }

    @Test
    void addTypeToExercise()
    {
        given()
                .when()
                .post(BASE_URL + "exercise/type?exerciseId=2&typeId=2")
                .then()
                .log().all()
                .statusCode(201);

        // Check that it was added
        List<ExerciseTypeDTO> dtos = given()
                .when()
                .get(BASE_URL + "exercise/2/type")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("", ExerciseTypeDTO.class);

        assertThat(dtos.size(), equalTo(1));
    }

    @Test
    void addEquipmentToExercise()
    {
        given()
                .when()
                .post(BASE_URL + "exercise/equipment?exerciseId=2&equipmentId=2")
                .then()
                .log().all()
                .statusCode(201);

        // Check that it was added
        List<EquipmentDTO> dtos = given()
                .when()
                .get(BASE_URL + "exercise/2/equipment")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList("", EquipmentDTO.class);

        assertThat(dtos.size(), equalTo(1));
    }
}