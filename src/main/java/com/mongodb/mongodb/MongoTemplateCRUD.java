// https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongodb-template-update

package com.mongodb.mongodb;

import com.mongodb.client.MongoClients;
import com.mongodb.mongodb.Student.Models.Address;
import com.mongodb.mongodb.Student.Models.Gender;
import com.mongodb.mongodb.Student.Models.Student;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.math.BigDecimal;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

public class MongoTemplateCRUD {

    private static final Log log = LogFactory.getLog(MongoTemplateCRUD.class);

    public static void main(String[] args) {

        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "amigoscode"));

        Student student = new Student(
                "John",
                "Doe",
                "doejohn@gmail.com",
                "pass",
                Gender.MALE,
                new Address(
                        "USA",
                        "New York",
                        "Wall Street",
                        "10005"
                ),
                List.of("Math", "Computer Science"),
                BigDecimal.TEN
        );

        // Insert
        insertOperation(mongoOps, student);

        // Find
        findByIdOperation(mongoOps, student);

        // Update
        updateOperation(mongoOps);

        // Delete
        deleteOperation(mongoOps, student);

        // Check that deletion worked
        findAllOperation(mongoOps);


//        mongoOps.dropCollection(Student.class);
    }

    private static void findAllOperation(MongoOperations mongoOps) {
        List<Student> students =  mongoOps.findAll(Student.class);
        log.info("Number of people = : " + students.size());
    }

    private static void deleteOperation(MongoOperations mongoOps, Student student) {
        // Delete
        mongoOps.remove(student);
    }

    private static void updateOperation(MongoOperations mongoOps) {
        Student student;
        mongoOps.updateFirst(query(where("firstName").is("Joe")), update("lastName", "Rose"), Student.class);
        student = mongoOps.findOne(query(where("firstName").is("Joe")), Student.class);
        log.info("Updated: " + student);
    }

    private static void findByIdOperation(MongoOperations mongoOps, Student student) {
        // Find
        student = mongoOps.findById(student.getId(), Student.class);
        log.info("Found: " + student);
    }

    private static void insertOperation(MongoOperations mongoOps, Student student) {
        // Insert is used to initially store the object into the database.
        mongoOps.insert(student);
        log.info("Insert: " + student);
    }
}