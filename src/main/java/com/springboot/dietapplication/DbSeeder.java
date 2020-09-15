package com.springboot.dietapplication;

import com.springboot.dietapplication.model.base.DocRef;
import com.springboot.dietapplication.model.base.Header;
import com.springboot.dietapplication.model.dish.Dish;
import com.springboot.dietapplication.model.patient.Patient;
import com.springboot.dietapplication.model.product.AmountType;
import com.springboot.dietapplication.model.product.Category;
import com.springboot.dietapplication.model.product.ProductForDish;
import com.springboot.dietapplication.model.product.Product;
import com.springboot.dietapplication.model.excel.ProductExcel;
import com.springboot.dietapplication.model.user.User;
import com.springboot.dietapplication.repository.*;
import io.github.biezhi.excel.plus.Reader;
import org.joda.time.DateTime;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

@Component
public class DbSeeder implements CommandLineRunner {

    private ProductRepository productRepository;
    private DishRepository dishRepository;
    private UserRepository userRepository;
    private CategoryRepository categoryRepository;
    private PatientRepository patientRepository;

    public DbSeeder(ProductRepository productRepository, DishRepository dishRepository,
                    UserRepository userRepository, CategoryRepository categoryRepository,
                    PatientRepository patientRepository) {
        this.productRepository = productRepository;
        this.dishRepository = dishRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public void run(String... args) {

        this.productRepository.deleteAll();
        this.dishRepository.deleteAll();
        this.userRepository.deleteAll();
        this.categoryRepository.deleteAll();
        this.patientRepository.deleteAll();

        User user1 = new User("aaa",
                "$2y$12$xQyJdsoamI/19a4p3bgRcOj2KeLpxPWj3.whkTrjz2jzIbO9fnr6m", "imageId");
        userRepository.save(user1);

        User user = new User("name", "password", "imageId");
        Header header = new Header();
        header.setCreatedBy(DocRef.fromDoc(user));

        List<ProductExcel> importedProducts = new ArrayList<>();

        List<String> fileImportList = Arrays.asList(
                "ProductData/Stage5.xlsx",
                "ProductData/Stage8.xlsx");

        for (String filePath : fileImportList) {
            try {
                URL url = getClass().getClassLoader().getResource(filePath);
                assert url != null;
                File file = Paths.get(url.toURI()).toFile();

                importedProducts = Reader.create(ProductExcel.class)
                        .from(file)
                        .start(1)
                        .asList();
            } catch (IllegalArgumentException e) {
                System.out.println("File not found");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Set<String> subcategories = new TreeSet<>();

            String category = "";
            List<Product> products = new ArrayList<>();
            for (ProductExcel productExcel : importedProducts) {
                if (!productExcel.getCategory().equals(category)) {
                    if (!category.equals("")) {
                        Category sendCategory = new Category();
                        sendCategory.setCategory(category);
                        sendCategory.setSubcategories(subcategories);
                        this.categoryRepository.save(sendCategory);
                    }
                    subcategories.clear();
                    category = productExcel.getCategory();
                }
                subcategories.add(productExcel.getSubcategory());

                Product product = new Product(productExcel);
                long currentTime = new DateTime().getMillis();
                product.setHeader(header);
                product.getHeader().setCreatedEpochMillis(currentTime);
                products.add(product);
            }
            if (!category.equals("")) {
                Category sendCategory = new Category();
                sendCategory.setCategory(category);
                sendCategory.setSubcategories(subcategories);
                this.categoryRepository.save(sendCategory);
            }

            this.productRepository.saveAll(products);
        }

        List<Product> cukier = this.productRepository.findByNameLike("Cukier");
        List<Product> karmelki = this.productRepository.findByNameLike("Karmelki nadziewane");
        List<Product> kawa = this.productRepository.findByNameLike("Kawa");

        List<ProductForDish> productsToAdd = new ArrayList<>();
        ProductForDish productForDish = new ProductForDish(DocRef.fromDoc(cukier.get(0)), 12.0f, AmountType.PIECE);
        ProductForDish productForDish2 = new ProductForDish(DocRef.fromDoc(karmelki.get(0)), 0.3f, AmountType.G);
        ProductForDish productForDish3 = new ProductForDish(DocRef.fromDoc(kawa.get(0)), 200.0f, AmountType.ML);

        productsToAdd.add(productForDish);
        productsToAdd.add(productForDish2);

        Dish dish = new Dish(productsToAdd);
        dish.setName("Karmelki cukrowane");
        dish.setPrimaryImageId("Zdjecie karmelkow");
        dish.setPortions(3.0f);
        dish.setRecipe("Połącz karmelki z cukrem");
        this.dishRepository.save(dish);

        productsToAdd.add(productForDish3);
        Dish dish2 = new Dish(productsToAdd);
        dish2.setName("Karmelki cukrowane z kawą");
        dish2.setPrimaryImageId("Zdjecie karmelkow");
        dish2.setPortions(4.0f);
        dish2.setRecipe("Połącz karmelki z cukrem i kawusią");
        this.dishRepository.save(dish2);

        Patient patient = new Patient();
        patient.setBirthDate("11111111");
        patient.setBodyWeight(120);
        patient.setName("Pacjent");
        patient.setEmail("email@email.com");
        patient.setNumberPhone("123456789");
        patient.setDietaryPurpose("Schudnąć xD");
        this.patientRepository.save(patient);

        System.out.println(dish.getId());
    }
}
