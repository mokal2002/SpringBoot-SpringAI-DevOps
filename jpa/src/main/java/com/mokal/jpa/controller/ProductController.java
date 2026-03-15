package com.mokal.jpa.controller;

import com.mokal.jpa.entity.Product;
import com.mokal.jpa.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final int PAGE_SIZE = 5;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

//    @GetMapping
//    public List<Product> findByOrderByPrice(){
//        return productRepository.findByOrderByPrice();
//    }


//    http://localhost:8080/products?sortBy=quantity&pageNumber=0&title=maggi
    @GetMapping
    public List<Product> getAllProducts(
            @RequestParam(defaultValue = "") String title,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "0") Integer pageNumber
    ){

        return productRepository.findByTitleContainingIgnoreCase(
                title,
                PageRequest.of(pageNumber,PAGE_SIZE,Sort.by(sortBy))
        );

//        return productRepository.findBy(Sort.by(Sort.Direction.ASC,sortBy,"price"));
//            return productRepository.findBy(Sort.by(
//                    Sort.Order.desc(sortBy),
//                    Sort.Order.desc("title")
//                    )
//            );

//        Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE,Sort.by(sortBy));
//        return productRepository.findAll(pageable).getContent();
    }
}
