package infrastructure;

import domain.booklet.Booklet;
import org.springframework.data.repository.CrudRepository;

public interface BookletRepository extends CrudRepository<Booklet, Long> {
}
