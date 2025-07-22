package ddalkak.presigned_url.dto;

import ddalkak.presigned_url.enums.FileMime;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record FileMetaData(String baseName,
                           String mime) {
    private static final String DEFAULT_MIME = "application/octet-stream";
    
    public static FileMetaData from(String fullFileName) {
        int dotIdx = fullFileName.lastIndexOf('.');
        validateExtension(fullFileName, dotIdx);
        return FileMetaData.builder()
                .baseName(fullFileName.substring(0, dotIdx))
                .mime(transferExtensionToMime(fullFileName, dotIdx))
                .build();
    }

    private static String transferExtensionToMime(String fullFileName, int dotIdx) {
        return FileMime.fromExtension(fullFileName.substring(dotIdx))
                .map(FileMime::getMime)
                .orElse(DEFAULT_MIME);
    }

    private static void validateExtension(String fullFileName, int dotIdx) {
        if (dotIdx == -1 || dotIdx == fullFileName.length() - 1) {
            throw new IllegalArgumentException("Invalid extension");
        }
    }
}
