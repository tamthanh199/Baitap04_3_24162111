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

    private static final Path PROFILE_DIRECTORY = Paths.get(
            System.getProperty("user.home"),
            "ServletCrudMvcUploads",
            "profile");

    private FileUploadUtil() {
    }

    public static String saveCategoryImage(Part part) throws IOException {
        return saveImage(part, CATEGORY_DIRECTORY);
    }

    public static String saveProfileImage(Part part) throws IOException {
        return saveImage(part, PROFILE_DIRECTORY);
    }

    private static String saveImage(Part part, Path directory) throws IOException {

        if (part == null
                || part.getSize() == 0
                || part.getSubmittedFileName() == null
                || part.getSubmittedFileName().isBlank()) {

            return null;
        }

        String originalFileName = Paths.get(part.getSubmittedFileName())
                .getFileName()
                .toString();

        int dotIndex = originalFileName.lastIndexOf('.');

        if (dotIndex < 0) {
            throw new IOException("File anh phai co phan mo rong.");
        }

        String extension = originalFileName
                .substring(dotIndex)
                .toLowerCase(Locale.ROOT);

        if (!extension.equals(".jpg")
                && !extension.equals(".jpeg")
                && !extension.equals(".png")
                && !extension.equals(".gif")
                && !extension.equals(".webp")) {

            throw new IOException(
                    "Chi chap nhan file JPG, JPEG, PNG, GIF hoac WEBP.");
        }

        Files.createDirectories(directory);

        String savedFileName = UUID.randomUUID() + extension;

        Path destination = directory.resolve(savedFileName);

        try (InputStream inputStream = part.getInputStream()) {

            Files.copy(
                    inputStream,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING);
        }

        return savedFileName;
    }

    public static Path getCategoryImagePath(String fileName) {
        return CATEGORY_DIRECTORY.resolve(fileName).normalize();
    }

    public static Path getProfileImagePath(String fileName) {
        return PROFILE_DIRECTORY.resolve(fileName).normalize();
    }

    public static void deleteCategoryImage(String fileName) throws IOException {

        deleteImage(fileName, CATEGORY_DIRECTORY);
    }

    public static void deleteProfileImage(String fileName) throws IOException {

        deleteImage(fileName, PROFILE_DIRECTORY);
    }

    private static void deleteImage(String fileName, Path directory)
            throws IOException {

        if (fileName == null || fileName.isBlank()) {
            return;
        }

        Path normalizedDirectory =
                directory.toAbsolutePath().normalize();

        Path imagePath =
                normalizedDirectory.resolve(fileName).normalize();

        if (!imagePath.startsWith(normalizedDirectory)) {
            throw new IOException("Duong dan file khong hop le.");
        }

        Files.deleteIfExists(imagePath);
    }
}