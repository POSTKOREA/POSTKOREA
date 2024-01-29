package com.example.demo.service;

import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SignatureException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.max-file-size}")
    private String maxFileSize;

    private final MemberRepository memberRepository;

    @Transactional
    public String uploadProfileImage(Long id, MultipartFile file) throws SignatureException, IOException {

        // 잘못된 토큰을 입력받은 경우
        if (!memberRepository.existsById(id)) {
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다.");
        }
        
        // 파일의 용량이 5MB를 초과하는 경우
        long parsedMaxFileSize = DataSize.parse(maxFileSize).toBytes();
        if (file.getSize() > parsedMaxFileSize) {
            throw new MaxUploadSizeExceededException(parsedMaxFileSize);
        }

        // 저장 시 중복 방지를 위해 사용자 아이디로 파일명 수정
        String fileName = id + ".PNG";
        Path filePath = Paths.get(uploadDir, fileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // userId 값으로 저장하니까 굳이 profile_img 컬럼이 필요가 없음
//        memberRepository.updateProfileImage(userId, filePath.toString());
        return fileName;
    }

    public Resource downloadImage(Long id) throws MalformedURLException {

        // 잘못된 토큰을 입력받은 경우
        if (!memberRepository.existsById(id)) {
            throw new EntityNotFoundException("사용자를 찾을 수 없습니다.");
        }

        String fileName = id + ".PNG";
        Path filePath = Paths.get(uploadDir, fileName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new EntityNotFoundException("파일을 찾을 수 없습니다.");
        }

        return resource;
    }
}
