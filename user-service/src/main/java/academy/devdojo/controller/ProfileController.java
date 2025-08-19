package academy.devdojo.controller;


import academy.devdojo.mapper.ProfileMapper;
import academy.devdojo.request.ProfilePostRequest;
import academy.devdojo.response.ProfileGetResponse;
import academy.devdojo.response.ProfilePostResponse;
import academy.devdojo.service.ProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/profiles")
@Slf4j
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService service;
    private final ProfileMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProfileGetResponse>> findAll(@RequestParam(required = false) String name) {
        log.debug("Request received to find all users, param name '{}'", name);
        var profiles = service.findAll(name);
        var profilesGetResponse = mapper.toProfileGetResponseList(profiles);
        return ResponseEntity.ok(profilesGetResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<ProfileGetResponse> findById(@PathVariable Long id) {
        log.debug("Request received to find profile by id {}", id);
        var profileFound = service.findByIdOrThrowNotFound(id);
        var profileGetResponse = mapper.toProfileGetResponse(profileFound);
        return ResponseEntity.ok(profileGetResponse);
    }

    @PostMapping
    public ResponseEntity<ProfilePostResponse> save(@RequestBody @Valid ProfilePostRequest request) {
        log.debug("Request received to save profile {}", request);
        var profile = mapper.toProfile(request);
        var profileSaved = service.save(profile);
        var profilePostResponse = mapper.toProfilePostResponse(profileSaved);

        return ResponseEntity.status(HttpStatus.CREATED).body(profilePostResponse);
    }
}
