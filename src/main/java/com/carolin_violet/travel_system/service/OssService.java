package com.carolin_violet.travel_system.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String uploadFileAvatar(MultipartFile file, String FolderName);

    void removeRemotePhoto(String url);
}
