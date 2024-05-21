package tr.edu.marmara.petcare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.edu.marmara.petcare.model.Document;
import tr.edu.marmara.petcare.model.User;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    Document findDocumentByVeterinary(User veterinary);
}
