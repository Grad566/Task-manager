package hexlet.code.app.mapper;

import hexlet.code.app.dto.UserCreatedDTO;
import hexlet.code.app.dto.UserDTO;
import hexlet.code.app.dto.UserUpdatedDTO;
import hexlet.code.app.model.User;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Mapper(
        uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {
    @Autowired
    private BCryptPasswordEncoder encoder;

    @Mapping(target = "passwordDigest", source = "password")
    public abstract User map(UserCreatedDTO dto);
    public abstract UserDTO map(User model);
    public abstract void update(UserUpdatedDTO dto, @MappingTarget User model);

    @BeforeMapping
    public void encryptPassword(UserCreatedDTO dto) {
        var password = dto.getPassword();
        dto.setPassword(encoder.encode(password));
    }
}
