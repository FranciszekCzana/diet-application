package com.springboot.dietapplication.controller.mongo;

import com.springboot.dietapplication.model.mongo.product.MongoProduct;
import com.springboot.dietapplication.model.type.ProductType;
import com.springboot.dietapplication.service.mongo.MongoProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/mongo/products")
public class MongoProductController {

    private final MongoProductService productService;

    public MongoProductController(MongoProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductType> getAll() {
        return this.productService.getAll();
    }

    @GetMapping(path = "/{productId}")
    public ProductType getProductById(@PathVariable("productId") String productId) {
        return this.productService.getProductById(productId);
    }

    @GetMapping(path = "/dishlist/{dishId}")
    public List<ProductType> getProductsByDishId(@PathVariable("dishId") String dishId) {
        return this.productService.getProductsByDishId(dishId);
    }

    @GetMapping(path = "/{category}/{subcategory}")
    public List<ProductType> getFilteredProducts(@PathVariable("category") String category,
                                                  @PathVariable("subcategory") String subcategory) {
        return this.productService.getFilteredProducts(category, subcategory);
    }

    @GetMapping(path = "/menu/{menuId}")
    public Map<String, ProductType> getMenuProducts(@PathVariable("menuId") String menuId) {
        return this.productService.getMenuProducts(menuId);
    }

    @GetMapping(path = "/name/{name}")
    public List<MongoProduct> getFilteredProducts(@PathVariable("name") String name) {
        return this.productService.getFilteredProducts(name);
    }

    @PostMapping(produces = "application/json")
    ResponseEntity<ProductType> insert(@RequestBody ProductType productType) {
        ProductType product = this.productService.insert(productType);
        return ResponseEntity.ok().body(product);
    }

    @PutMapping(path = "/{productId}", produces = "application/json")
    ResponseEntity<ProductType> update(@RequestBody ProductType productType) {
        ProductType product = this.productService.insert(productType);
        return ResponseEntity.ok().body(product);
    }

    @DeleteMapping(path = "/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        return this.productService.delete(id);
    }
}
