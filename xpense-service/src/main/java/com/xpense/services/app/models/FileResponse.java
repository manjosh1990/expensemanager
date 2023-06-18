package com.xpense.services.app.models;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private String fileName;
    private String fileContentType;
    private String fileDownloadURI;
    private long fileSize;
}
