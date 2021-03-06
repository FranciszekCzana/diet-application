import { Component, OnInit } from '@angular/core';
import {MealService} from "../../service/meal.service";
import {NotificationService} from "../../service/notification.service";
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {ProductSelectComponent} from "../../products/product-select/product-select.component";
import {FormArray} from "@angular/forms";
import {MenuService} from "../../service/menu.service";
import {DishSelectComponent} from "../../dishes/dish-select/dish-select.component";
import {ProductService} from "../../service/product.service";

@Component({
  selector: 'app-meal-add',
  templateUrl: './meal-add.component.html',
  styleUrls: ['./meal-add.component.css']
})
export class MealAddComponent implements OnInit {

  constructor(
    private service: MealService,
    private menuService: MenuService,
    private productService: ProductService,
    private notificationService: NotificationService,
    private dialog: MatDialog,
    public dialogRef: MatDialogRef<MealAddComponent>
  ) { }

  amountTypes = [
    { id: "PIECE", value: "Sztuka" },
    { id: "SPOON", value: "Łyżka" },
    { id: "SCOOPS", value: "Miarka" },
    { id: "HANDFUL", value: "Garść" },
    { id: "SLICE", value: "Plaster" },
    { id: "LEAVES", value: "Listek" },
    { id: "GLASS", value: "Szklanka" },
  ];

  foodTypes = [
    { id: "BREAKFEST", value: "Śniadanie" },
    { id: "BRUNCH", value: "II śniadanie" },
    { id: "DINNER", value: "Obiad" },
    { id: "TEA", value: "Podwieczorek"},
    { id: "SUPPER", value: "Kolacja" },
    { id: "PRE_WORKOUT", value: "Przedtreningówka" },
    { id: "POST_WORKOUT", value: "Potreningówka"},
  ];

  blockProduct = false;
  blockDish = false;
  tabIndex = 0;

  ngOnInit(): void {
    if (this.service.form.get('id').value != null) {
      this.tabIndex = this.service.form.get('isProduct').value;
      let products = (<FormArray>this.service.form.get('productList'));
      if (this.tabIndex == 1) {
        this.blockDish = true;
        let productGrams = products.at(0).get('grams').value;
        setTimeout( () => {
          (<HTMLInputElement>document.getElementById("product_grams")).value = productGrams;
        });
      } else {
        this.blockProduct = true;
        for (let i = 0; i < products.length; i++) {
          let productId = products.at(i).get('productId').value;
          setTimeout( () => {
            (<HTMLInputElement>document.getElementById("name"+i)).value = this.productService.menuProductMap[productId].name;
          });
        }
      }
    }
  }

  onClear() {
    (<FormArray>this.service.form.get('productList')).clear();
    this.service.form.reset();
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }

  onSubmit() {
    if (this.service.form.valid) {
      if (!this.service.form.get('id').value) {
        this.service.insertMeal(this.service.form.value).subscribe();
        this.notificationService.success(":: Meal created successfully! ::");
      } else {
        this.service.updateMeal(this.service.form.value).subscribe();
        this.notificationService.success(":: Meal updated successfully! ::");
      }
      this.onClose();
    }
  }

  addProductButtonClick() {
    (<FormArray>this.service.form.get('productList')).push(this.service.addProductFormGroup());
  }

  onProductDeleteButtonClick(productIndex) {
    (<FormArray>this.service.form.get('productList')).removeAt(productIndex);
  }

  selectProductForDish(index) {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      if (result != null) {
        // Update value in HTML form
        this.productService.menuProductMap[result.id] = result;

        (<HTMLInputElement>document.getElementById("name"+index)).value = this.productService.menuProductMap[result.id].name;

        // Update value in Form Group
        let products = (<FormArray>this.service.form.get('productList'));
        products.at(index).get('productId').patchValue(result.id);
        this.service.form.patchValue({
          products: [products]
        });
      }
    });
  }

  selectProduct() {

    let dialogRef = this.dialog.open(ProductSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      if (result != null) {
        // Update value in HTML form
        this.productService.menuProductMap[result.id] = result;

        (<FormArray>this.service.form.get('productList')).clear();

        // Update value in Form Group
        this.service.form.get('name').patchValue(result.name);
        this.service.form.get('isProduct').patchValue(1);

        let productForm = this.service.addProductFormGroup();
        productForm.get('productId').patchValue(result.id);
        (<FormArray>this.service.form.get('productList')).push(productForm);

        let grams = (<HTMLInputElement>document.getElementById("product_grams")).value;
        if (grams == null || grams == "") {
          this.gramsChanged(100);
          (<HTMLInputElement>document.getElementById("product_grams")).value = "100";
        } else {
          this.gramsChanged(grams);
        }

      }
    });
  }

  selectDish() {

    let dialogRef = this.dialog.open(DishSelectComponent, {
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      if (result != null) {
        (<FormArray>this.service.form.get('productList')).clear();

        // Update value in Form Group
        this.service.form.get('name').patchValue(result.name);
        this.service.form.get('recipe').patchValue(result.recipe);
        this.service.form.get('isProduct').patchValue(0);

        for (let product of result.products) {
          let productForm = this.service.addProductFormGroup();
          productForm.get('productId').patchValue(product.productId);
          productForm.get('grams').patchValue(product.grams);
          productForm.get('amount').patchValue(product.amount);
          productForm.get('amountType').patchValue(product.amountType);
          (<FormArray>this.service.form.get('productList')).push(productForm);
        }
      }
    });
  }

  getProductSummary(index) {
    let product = this.service.form.get('productList').value[index];
    if (product == null || this.productService.menuProductMap[product.productId] == null) {
      return "";
    }

    let isProduct = (this.service.form.get('isProduct').value == 1);
    let grams = product.grams;

    let foodProperties = this.productService.menuProductMap[product.productId].foodProperties;

    let energy = (foodProperties.energyValue * grams) / 100;
    let proteins = (foodProperties.proteins * grams) / 100;
    let fats = (foodProperties.fats * grams) / 100;
    let carbohydrates = (foodProperties.carbohydrates * grams) / 100;

    if (isProduct) {
      let portions = this.service.form.get('portions').value;
      energy /= portions;
      proteins /= portions;
      fats /= portions;
      carbohydrates /= portions;
    }

    return "Kcal: " + energy.toFixed(2) +
      "    B: " + proteins.toFixed(2) +
      "    T: " + fats.toFixed(2) +
      "    W: " + carbohydrates.toFixed(2);
  }

  gramsChanged(grams) {
    this.service.form.get('productList').get('0').get('grams').patchValue(grams);
  }
}
