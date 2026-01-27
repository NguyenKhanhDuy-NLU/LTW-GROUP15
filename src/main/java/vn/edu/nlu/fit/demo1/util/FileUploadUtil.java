package vn.edu.nlu.fit.demo1.util;

import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUploadUtil {

    private static final String UPLOAD_DIRECTORY = "uploads/hotels";

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png", ".gif", ".webp"};

    public static String uploadFile(Part filePart, String uploadPath) throws IOException {
        if (filePart == null || filePart.getSize() == 0) {
            throw new IOException("File không hợp lệ hoặc rỗng");
        }

        if (filePart.getSize() > MAX_FILE_SIZE) {
            throw new IOException("File vượt quá kích thước cho phép (5MB)");
        }

        String originalFileName = getFileName(filePart);

        if (!isValidExtension(originalFileName)) {
            throw new IOException("Định dạng file không được hỗ trợ. Chỉ chấp nhận: jpg, jpeg, png, gif, webp");
        }

        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        File uploadDir = new File(uploadPath + File.separator + UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String filePath = uploadPath + File.separator + UPLOAD_DIRECTORY + File.separator + uniqueFileName;

        filePart.write(filePath);

        return "/" + UPLOAD_DIRECTORY + "/" + uniqueFileName;
    }

    public static List<String> uploadMultipleFiles(List<Part> fileParts, String uploadPath) {
        List<String> uploadedPaths = new ArrayList<>();

        for (Part filePart : fileParts) {
            try {
                if (filePart != null && filePart.getSize() > 0) {
                    String path = uploadFile(filePart, uploadPath);
                    uploadedPaths.add(path);
                }
            } catch (IOException e) {
                System.err.println("Lỗi upload file: " + e.getMessage());

            }
        }

        return uploadedPaths;
    }

    public static boolean deleteFile(String relativePath, String uploadPath) {
        try {
            String fullPath = uploadPath + relativePath;
            File file = new File(fullPath);

            if (file.exists()) {
                return file.delete();
            }
            return false;
        } catch (Exception e) {
            System.err.println("Lỗi xóa file: " + e.getMessage());
            return false;
        }
    }

    private static String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String content : contentDisposition.split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    private static boolean isValidExtension(String fileName) {
        if (fileName == null) return false;

        String lowerFileName = fileName.toLowerCase();
        for (String ext : ALLOWED_EXTENSIONS) {
            if (lowerFileName.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }

    public static String createThumbnail(String originalPath, String uploadPath, int width, int height) {

        return originalPath;
    }
}