package net.springboot.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.springboot.exception.ResourceNotFoundException;
import net.springboot.model.Product;
import net.springboot.repository.ProductRepository;

@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	@Autowired
    private ProductRepository productRepository;

	private static final Logger LOGGER = LogManager.getLogger(ProductServiceImpl.class);
	private static final String RECORD_NOT_FOUND_BY_ID = "RECORD_NOT_FOUND_BY_ID_MSG";
	Properties prop = new Properties();
	    
	public ProductServiceImpl() throws IOException {
		LOGGER.info("In " + new Exception().getStackTrace()[0].getClassName());
		try {
			prop.load(ProductServiceImpl.class.getClassLoader().getResourceAsStream("constants.properties"));
		} catch (Exception e) {
			LOGGER.debug(e.getMessage());
		}
	}
	
    @Override
    public Product createProduct(Product product) {
    	LOGGER.info("In " + new Exception().getStackTrace()[0].getMethodName());
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
    	LOGGER.info("In " + new Exception().getStackTrace()[0].getMethodName());
        Optional < Product > productDb = this.productRepository.findById(product.getId());

        if (productDb.isPresent()) {
            Product productUpdate = productDb.get();
            productUpdate.setId(product.getId());
            productUpdate.setName(product.getName());
            productUpdate.setDescription(product.getDescription());
            productRepository.save(productUpdate);
            return productUpdate;
        } else {
            throw new ResourceNotFoundException(prop.getProperty(RECORD_NOT_FOUND_BY_ID) + product.getId());
        }
    }

    @Override
    public List < Product > getAllProduct() {
    	LOGGER.info("In " + new Exception().getStackTrace()[0].getMethodName());
        return this.productRepository.findAll();
    }

    @Override
    public Product getProductById(long productId) {
    	LOGGER.info("In " + new Exception().getStackTrace()[0].getMethodName());
        Optional < Product > productDb = this.productRepository.findById(productId);

        if (productDb.isPresent()) {
            return productDb.get();
        } else {
            throw new ResourceNotFoundException(prop.getProperty(RECORD_NOT_FOUND_BY_ID) + productId);
        }
    }

    @Override
    public void deleteProduct(long productId) {
    	LOGGER.info("In " + new Exception().getStackTrace()[0].getMethodName());
        Optional < Product > productDb = this.productRepository.findById(productId);

        if (productDb.isPresent()) {
            this.productRepository.delete(productDb.get());
        } else {
            throw new ResourceNotFoundException(prop.getProperty(RECORD_NOT_FOUND_BY_ID) + productId);
        }
    }

}
