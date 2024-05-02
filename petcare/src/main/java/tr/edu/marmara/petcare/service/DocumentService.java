package tr.edu.marmara.petcare.service;

import org.springframework.stereotype.Service;
import tr.edu.marmara.petcare.dto.DocumentSaveRequest;
import tr.edu.marmara.petcare.model.Document;
import tr.edu.marmara.petcare.model.User;
import tr.edu.marmara.petcare.repository.DocumentRepository;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void saveDocument(User veterinary, DocumentSaveRequest documentSaveRequest) {
        Document document = Document.builder()
                .base64Document(documentSaveRequest.base64Document())
                .veterinary(veterinary)
                .build();
        documentRepository.save(document);
    }
}
