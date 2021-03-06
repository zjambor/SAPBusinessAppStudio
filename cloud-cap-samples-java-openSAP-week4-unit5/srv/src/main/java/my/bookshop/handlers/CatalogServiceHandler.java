package my.bookshop.handlers;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sap.cds.ql.Select;
import com.sap.cds.services.cds.CdsService;
import com.sap.cds.services.handler.EventHandler;
import com.sap.cds.services.handler.annotations.After;
import com.sap.cds.services.handler.annotations.ServiceName;
import com.sap.cds.services.persistence.PersistenceService;

import cds.gen.catalogservice.Books;
import cds.gen.catalogservice.Books_;
import cds.gen.catalogservice.CatalogService_;

@Component
@ServiceName(CatalogService_.CDS_NAME)
public class CatalogServiceHandler implements EventHandler {

	@Autowired
	PersistenceService db;

	@After(event = CdsService.EVENT_READ)
	public void discountBooks(Stream<Books> books) {
		books.filter(b -> b.getTitle() != null).filter(b -> {
			Integer stock = b.getStock();
			if(stock == null) {
				stock = db.run(Select.from(Books_.class).byId(b.getId()).columns(c -> c.stock())).single(Books.class).getStock();
			}
			return stock > 111;
		})
		.forEach(b -> b.setTitle(b.getTitle() + " -- 11% discount"));
	}

}