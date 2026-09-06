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

    private static final Path
            CATEGORY_DIRECTORY =
                    Paths.get(
                            System.getProperty(
                                    "user.home"),
                            "ServletCrudMvcUploads",
                            "category");

    private static final Path
            PROFILE_DIRECTORY =
                    Paths.get(
                            System.getProperty(
                                    "user.home"),
                            "ServletCrudMvcUploads",
                            "profile");

    private static final Path
            PRODUCT_DIRECTORY =
                    Paths.get(
                            System.getProperty(
                                    "user.home"),
                            "ServletCrudMvcUploads",
                            "product");

    private FileUploadUtil() {
    }

    public static String saveCategoryImage(
            Part part)
            throws IOException {

        return saveImage(
                part,
                CATEGORY_DIRECTORY);
    }

    public static String saveProfileImage(
            Part part)
            throws IOException {

        return saveImage(
                part,
                PROFILE_DIRECTORY);
    }

    public static String saveProductImage(
            Part part)
            throws IOException {

        return saveImage(
                part,
                PRODUCT_DIRECTORY);
    }

    private static String saveImage(
            Part part,
            Path directory)
            throws IOException {

        if (part == null
                || part.getSize() == 0
                || part.getSubmittedFileName() == null
                || part.getSubmittedFileName().isBlank()) {

            return null;
        }

        String originalFileName =
                Paths.get(
                        part.getSubmittedFileName())
                        .getFileName()
                        .toString();

        int dotIndex =
                originalFileName
                        .lastIndexOf('.');

        if (dotIndex < 0) {

            throw new IOException(
                    "File ảnh phải có phần mở rộng.");
        }

        String extension =
                originalFileName
                        .substring(dotIndex)
                        .toLowerCase(
                                Locale.ROOT);

        if (!extension.equals(".jpg")
                && !extension.equals(".jpeg")
                && !extension.equals(".png")
                && !extension.equals(".gif")
                && !extension.equals(".webp")) {

            throw new IOException(
                    "Chỉ chấp nhận JPG, JPEG, "
                    + "PNG, GIF hoặc WEBP.");
        }

        Files.createDirectories(
                directory);

        String savedFileName =
                UUID.randomUUID()
                        + extension;

        Path destination =
                directory.resolve(
                        savedFileName);

        try (InputStream inputStream =
                     part.getInputStream()) {

            Files.copy(
                    inputStream,
                    destination,
                    StandardCopyOption
                            .REPLACE_EXISTING);
        }

        return savedFileName;
    }

    public static Path
            getCategoryImagePath(
                    String fileName) {

        return safeResolve(
                CATEGORY_DIRECTORY,
                fileName);
    }

    public static Path
            getProfileImagePath(
                    String fileName) {

        return safeResolve(
                PROFILE_DIRECTORY,
                fileName);
    }

    public static Path
            getProductImagePath(
                    String fileName) {

        return safeResolve(
                PRODUCT_DIRECTORY,
                fileName);
    }

    public static void
            deleteCategoryImage(
                    String fileName)
            throws IOException {

        deleteImage(
                fileName,
                CATEGORY_DIRECTORY);
    }

    public static void
            deleteProfileImage(
                    String fileName)
            throws IOException {

        deleteImage(
                fileName,
                PROFILE_DIRECTORY);
    }

    public static void
            deleteProductImage(
                    String fileName)
            throws IOException {

        deleteImage(
                fileName,
                PRODUCT_DIRECTORY);
    }

    private static Path safeResolve(
            Path directory,
            String fileName) {

        Path normalizedDirectory =
                directory
                        .toAbsolutePath()
                        .normalize();

        Path imagePath =
                normalizedDirectory
                        .resolve(fileName)
                        .normalize();

        if (!imagePath.startsWith(
                normalizedDirectory)) {

            throw new
                    IllegalArgumentException(
                            "Đường dẫn file không hợp lệ.");
        }

        return imagePath;
    }

    private static void deleteImage(
            String fileName,
            Path directory)
            throws IOException {

        if (fileName == null
                || fileName.isBlank()) {

            return;
        }

        Path imagePath =
                safeResolve(
                        directory,
                        fileName);

        Files.deleteIfExists(
                imagePath);
    }
}