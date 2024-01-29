package com.example.demo.controller;

import com.example.demo.service.MemberService;
import com.example.demo.service.ProfileService;
import com.example.demo.utils.AuthTokensGenerator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/profile")
public class ProfileController {

    private final MemberService memberService;
    private final ProfileService profileService;
    private final AuthTokensGenerator authTokensGenerator;

    // 사용자
    @PostMapping("/image")
    public ResponseEntity<?> uploadProfileImage(
            @RequestHeader("Authorization") String token,
            @RequestPart MultipartFile file) {
        try {
            Long userId = authTokensGenerator.extractMemberId(token);
            String fileName = profileService.uploadProfileImage(userId, file);

            Map<String, Object> response = new HashMap<>();
            response.put("code", 0);
            response.put("msg", "succeed");
            response.put("data", fileName);

            return ResponseEntity.ok(response); // 200
        } catch (SignatureException | EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 1);
            response.put("msg", e.getMessage());    // 사용자를 찾을 수 없습니다.

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        // 중요: Global Exception Handler 필요
        // MaxUploadSizeExceededException 같은 경우 컨트롤러 매서드 바깥에서 예외가 발생하기에 catch 불가능함
        // ControllerAdvice 를 통해 ExceptionHandler 기능 구현 필요
        catch (MaxUploadSizeExceededException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 2);
            response.put("msg", "파일의 용량이 " + e.getMessage() + "를 초과하였습니다.");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 3);
            response.put("msg", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    // 이미지 조회 시, 친구 등 다른 사용자의 프로필 이미지도 조회할 수 있어야함
    // 지금은 토큰을 통해 조회가능하며 차후 토큰 -> userId -> 친구목록 조회 기능도 추가 예정
    @GetMapping("/image")
    public ResponseEntity<?> downloadProfileImage(@RequestHeader("Authorization") String token) {
        try {
            Long userId = authTokensGenerator.extractMemberId(token);
            Resource resource = profileService.downloadImage(userId);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(resource);
        } catch (EntityNotFoundException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 1);
            response.put("msg", e.getMessage());    // 사용자를 찾을 수 없습니다.

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (MalformedURLException e) {
            Map<String, Object> response = new HashMap<>();
            response.put("code", 2);
            response.put("msg", e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
