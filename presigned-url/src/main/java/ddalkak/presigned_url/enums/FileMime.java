package ddalkak.presigned_url.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum FileMime {
    PNG(".png", "image/png"),
    JPG(".jpg", "image/jpeg"),
    JPEG(".jpeg", "image/jpeg");

    private final String extension;
    private final String mime;

    public static Optional<FileMime> fromExtension(String extension) {
        return Arrays.stream(values())
                .filter(fileMime -> fileMime.extension.equalsIgnoreCase(extension))
                .findFirst();
    }
}
