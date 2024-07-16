package hexlet.code.app.util;

//import hexlet.code.app.dto.UserCreatedDTO;
//import hexlet.code.app.dto.UserUpdatedDTO;
//import net.datafaker.Faker;
//import org.instancio.Instancio;
//import org.instancio.Select;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.openapitools.jackson.nullable.JsonNullable;
//
//@Component
//public class ModelGenerator {
//    @Autowired
//    private Faker faker;
//
//    public UserCreatedDTO getUserCreatedDTO() {
//        return Instancio.of(UserCreatedDTO.class)
//                .supply(Select.field(UserCreatedDTO::getEmail), () -> faker.internet().emailAddress())
//                .supply(Select.field(UserCreatedDTO::getFirstName), () -> JsonNullable.of(faker.name().firstName()))
//                .supply(Select.field(UserCreatedDTO::getLastName), () -> JsonNullable.of(faker.name().lastName()))
//                .supply(Select.field(UserCreatedDTO::getPassword), () -> faker.internet().password())
//                .create();
//    }
//
//    public UserUpdatedDTO getUserUpdatedDTO() {
//        return Instancio.of(UserUpdatedDTO.class)
//                .supply(Select.field(UserUpdatedDTO::getEmail), () -> JsonNullable.of(faker.internet().emailAddress()))
//                .supply(Select.field(UserUpdatedDTO::getPassword), () -> JsonNullable.of(faker.internet().password()))
//                .create();
//    }
//}
