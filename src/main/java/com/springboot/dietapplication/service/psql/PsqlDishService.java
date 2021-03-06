package com.springboot.dietapplication.service.psql;

import com.springboot.dietapplication.model.psql.menu.PsqlAmountType;
import com.springboot.dietapplication.model.psql.menu.PsqlFoodType;
import com.springboot.dietapplication.model.type.AmountType;
import com.springboot.dietapplication.model.type.FoodType;
import com.springboot.dietapplication.model.type.ProductDishType;
import com.springboot.dietapplication.model.psql.dish.PsqlDish;
import com.springboot.dietapplication.model.psql.dish.PsqlProductDish;
import com.springboot.dietapplication.model.type.DishType;
import com.springboot.dietapplication.repository.psql.PsqlAmountTypeRepository;
import com.springboot.dietapplication.repository.psql.PsqlDishRepository;
import com.springboot.dietapplication.repository.psql.PsqlFoodTypeRepository;
import com.springboot.dietapplication.repository.psql.PsqlProductDishRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PsqlDishService {

    private final PsqlDishRepository dishRepository;
    private final PsqlProductDishRepository productDishRepository;
    private final PsqlFoodTypeRepository foodTypeRepository;
    private final PsqlAmountTypeRepository amountTypeRepository;

    public PsqlDishService(PsqlDishRepository dishRepository,
                           PsqlProductDishRepository productDishRepository,
                           PsqlFoodTypeRepository foodTypeRepository,
                           PsqlAmountTypeRepository amountTypeRepository) {
        this.dishRepository = dishRepository;
        this.productDishRepository = productDishRepository;
        this.foodTypeRepository = foodTypeRepository;
        this.amountTypeRepository = amountTypeRepository;
    }

    public List<DishType> getAll() {
        List<PsqlDish> dishes = this.dishRepository.findAll();
        return convertLists(dishes);
    }

    public DishType getDishById(Long dishId) {
        return new DishType();
    }

    public ResponseEntity<DishType> insert(DishType dish) {
        PsqlDish psqlDish = new PsqlDish(dish);
        this.dishRepository.save(psqlDish);

        if (psqlDish.getId() > 0)
            this.productDishRepository.deletePsqlProductDishesByDishId(psqlDish.getId());

        for (ProductDishType productDish : dish.getProducts()) {
            PsqlProductDish psqlProductDish = new PsqlProductDish(productDish);
            psqlProductDish.setDishId(psqlDish.getId());

            PsqlAmountType amountType = this.amountTypeRepository.getPsqlAmountTypeByName(productDish.getAmountType().toString());
            psqlProductDish.setAmountTypeId(amountType.getId());

            this.productDishRepository.save(psqlProductDish);
        }

        PsqlFoodType foodType = this.foodTypeRepository.getPsqlFoodTypeByName(dish.getFoodType().toString());
        psqlDish.setFoodTypeId(foodType.getId());
        this.dishRepository.save(psqlDish);

        dish.setId(String.valueOf(psqlDish.getId()));

        return ResponseEntity.ok().body(dish);
    }

    public ResponseEntity<Void> delete(Long id) {
        List<PsqlProductDish> productDishList =
                this.productDishRepository.findPsqlProductDishesByDishId(id);
        this.productDishRepository.deleteAll(productDishList);

        this.dishRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private List<DishType> convertLists(List<PsqlDish> psqlDayMeals) {
        List<DishType> dayMealTypeList = new ArrayList<>();
        for (PsqlDish dayMeal : psqlDayMeals) {
            dayMealTypeList.add(convertPsqlDishToDishType(dayMeal));
        }
        return dayMealTypeList;
    }

    private DishType convertPsqlDishToDishType(PsqlDish psqlDish) {
        DishType dishType = new DishType(psqlDish);

        List<ProductDishType> productList = new ArrayList<>();
        List<PsqlProductDish> productDishList =
                this.productDishRepository.findPsqlProductDishesByDishId(psqlDish.getId());
        for (PsqlProductDish productDish : productDishList) {
            ProductDishType productDishType = new ProductDishType(productDish);

            Optional<PsqlAmountType> amountTypeOptional = this.amountTypeRepository.findById(productDish.getAmountTypeId());
            if (amountTypeOptional.isPresent()) {
                AmountType amountType = AmountType.valueOf(amountTypeOptional.get().getName());
                productDishType.setAmountType(amountType);
            }

            productList.add(productDishType);
        }

        Optional<PsqlFoodType> foodType = this.foodTypeRepository.findById(psqlDish.getFoodTypeId());
        foodType.ifPresent(psqlFoodType -> dishType.setFoodType(FoodType.valueOf(psqlFoodType.getName())));

        dishType.setProducts(productList);

        return dishType;
    }

}
