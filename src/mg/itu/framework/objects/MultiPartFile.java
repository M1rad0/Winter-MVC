package mg.itu.framework.objects;

import jakarta.servlet.http.Part;

import java.io.*;

public class MultiPartFile {
    private final Part part;

    // Constructeur pour initialiser à partir d'un Part
    public MultiPartFile(Part part) {
        if (part == null) {
            throw new IllegalArgumentException("Part ne peut pas être null.");
        }
        this.part = part;
    }

    // Retourne le nom du champ (attribut 'name' du formulaire)
    public String getName() {
        return part.getName();
    }

    // Retourne le nom original du fichier
    public String getOriginalFilename() {
        return part.getSubmittedFileName();
    }

    // Retourne le type MIME du fichier
    public String getContentType() {
        return part.getContentType();
    }

    // Retourne la taille du fichier en octets
    public long getSize() {
        return part.getSize();
    }

    // Retourne le contenu du fichier sous forme de tableau d'octets
    public byte[] getBytes() throws IOException {
        try (InputStream inputStream = part.getInputStream()) {
            return inputStream.readAllBytes();
        }
    }

    // Retourne un InputStream pour lire le contenu du fichier
    public InputStream getInputStream() throws IOException {
        return part.getInputStream();
    }

    // Vérifie si le fichier est vide
    public boolean isEmpty() {
        return part.getSize() == 0;
    }

    // Transfère le contenu du fichier à un emplacement sur disque
    public void transferTo(File dest) throws IOException {
        if (dest == null) {
            throw new IllegalArgumentException("Le fichier de destination ne peut pas être null.");
        }

        try (InputStream inputStream = part.getInputStream();
             OutputStream outputStream = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    // Retourne le Part sous-jacent (optionnel pour des usages avancés)
    public Part getPart() {
        return this.part;
    }
}