package hexlet.code.app.mapper;

import hexlet.code.app.dto.UserCreatedDTO;
import hexlet.code.app.dto.UserDTO;
import hexlet.code.app.dto.UserUpdatedDTO;
import hexlet.code.app.model.User;

import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
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

    public abstract User map(UserCreatedDTO dto);

    public abstract UserDTO map(User model);

    public abstract void update(UserUpdatedDTO dto, @MappingTarget User model);

    @BeforeMapping
    public void encryptPassword(UserCreatedDTO dto) {
        var password = dto.getPassword();
        dto.setPassword(encoder.encode(password));
    }

    @BeforeMapping
    public void encryptPassword(UserUpdatedDTO dto, @MappingTarget User model) {
        if (dto.getPassword() != null && dto.getPassword().isPresent()) {
            String password = dto.getPassword().get();
            model.setPassword(encoder.encode(password));
        }
    }
}
