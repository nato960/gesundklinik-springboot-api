package personal.GesundKlinik.modules.user.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import personal.GesundKlinik.modules.user.dto.UserRegisterRequest;
import personal.GesundKlinik.modules.user.dto.UserRegisterResponse;
import personal.GesundKlinik.modules.user.mapper.IUserMapper;
import personal.GesundKlinik.modules.user.service.IUserService;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Users")
public class UserController {

    private final IUserService service;
    private final IUserMapper mapper;

    @PostMapping
    public ResponseEntity<UserRegisterResponse> createUser(
            @RequestBody @Valid final UserRegisterRequest request){

        var entity = mapper.toEntity(request);
        var savedEntity = service.save(entity);
        var response = mapper.toSaveResponse(savedEntity);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
