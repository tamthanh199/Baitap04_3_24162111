package vn.hcmute.webpr330479.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.UUID;

import jakarta.servlet.http.Part;

public final class FileUploadUtil {

    private static final Path CATEGORY_DIRECTORY = Paths.get(
            System.getProperty("user.home"),
            "ServletCrudMvcUploads",
            "category");

    private FileUploadUtil() {
    }

    public static String saveCategoryImage(Part part) throws IOException {
        if (part == null || part.getSize() == 0 || part.getSubmittedFileName() == null) {
            return null;
        }

        String originalFileName = Paths.get(part.getSubmittedFileName())
                .getFileName()
                .toString();

        int dotIndex = originalFileName.lastIndexOf('.');
        if (dotIndex < 0) {
            throw new IOException("File anh phai co phan mo rong.");
        }

        String extension = originalFileName.substring(dotIndex).toLowerCase(Locale.ROOT);

        if (!extension.equals(".jpg") && !extension.equals(".jpeg")
                && !extension.equals(".png") && !extension.equals(".gif")
                && !extension.equals(".webp")) {
            throw new IOException("Chi chap nhan file JPG, JPEG, PNG, GIF hoac WEBP.");
        }

        Files.createDirectories(CATEGORY_DIRECTORY);

        String savedFileName = UUID.randomUUID() + extension;
        Path destination = CATEGORY_DIRECTORY.resolve(savedFileName);

        try (InputStream inputStream = part.getInputStream()) {
            Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
        }

        return savedFileName;
    }

    public static Path getCategoryImagePath(String fileName) {
        return CATEGORY_DIRECTORY.resolve(fileName).normalize();
    }
    public static void deleteCategoryImage(String fileName) throws IOException {
        if (fileName == null || fileName.isBlank()) {
            return;
        }

        Path directory = CATEGORY_DIRECTORY.toAbsolutePath().normalize();
        Path imagePath = directory.resolve(fileName).normalize();

        if (!imagePath.startsWith(directory)) {
            throw new IOException("Duong dan file khong hop le.");
        }

        Files.deleteIfExists(imagePath);
    }
}
