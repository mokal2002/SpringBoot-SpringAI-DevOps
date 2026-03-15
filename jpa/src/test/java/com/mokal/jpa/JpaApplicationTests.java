package com.mokal.jpa;

import com.mokal.jpa.entity.Product;
import com.mokal.jpa.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.spel.ast.OpAnd;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class JpaApplicationTests {
    @Autowired
    ProductRepository productRepository;


	@Test
	void contextLoads() {
	}

    @Test
    void testRepo(){
        Product product = Product.builder()
                .sku("nestle34")
                .title("Nestle")
                .price(BigDecimal.valueOf(34.4))
                .quantity(23)
                .build();
        Product product1 = productRepository.save(product);
        System.out.println(product1);
    }

    @Test
    void getRepo(){
        List<Product> products =productRepository.findAll();
        System.out.println(products);
    }

    @Test
    void getByTitle(){
        Product product = productRepository.findByTitle("Pepsi");
        System.out.println(product);
    }

    @Test
    void  getByDate(){
        List<Product> product = productRepository.findByCreatedAtAfter(LocalDateTime.of(2026,1,1,0,0,0));
        System.out.println(product);
    }

    @Test
    void getByQuntityAndPrice(){
        List<Product> products = productRepository.findByQuantityAndPrice(5,BigDecimal.valueOf(13.30));
        System.out.println(products);
    }

    @Test
    void getDataLikeTitle(){
        List<Product> products = productRepository.findByTitleLike("%pep%");
        System.out.println(products);
    }


    @Test
    void getByTitleAndPrice(){
        Optional<Product> product = productRepository.findByTitleAndPrice("Pepsi",BigDecimal.valueOf(13.3));
        product.ifPresent(System.out::println);
    }
}
