package repository;

import domain.Product;

public interface RawRepository {

    Product findOne(String id);
    Product[] returnAll();
    Product add(Product new_product);
    Product delete(Product product_to_delete);
    DTOcatalogView[] catalog();
}
