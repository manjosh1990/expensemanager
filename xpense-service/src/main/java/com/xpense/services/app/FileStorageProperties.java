package com.xpense.services.app;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
@Getter
@Setter
@Data
public class FileStorageProperties {
    private String uploadDir;
}
