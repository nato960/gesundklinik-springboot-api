package personal.GesundKlinik.modules.user.controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import personal.GesundKlinik.modules.user.dto.UserRegisterRequest;
import personal.GesundKlinik.modules.user.dto.UserRegisterResponse;
import personal.GesundKlinik.modules.user.mapper.IUserMapper;
import personal.GesundKlinik.modules.user.service.IUserService;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/users")
@Tag(name = "Users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService service;
    private final IUserMapper mapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public UserRegisterResponse save(@RequestBody @Valid final UserRegisterRequest request){
        var entity = mapper.toEntity(request);
        var savedEntity = service.save(entity);
        return mapper.toSaveResponse(savedEntity);
    }
}
