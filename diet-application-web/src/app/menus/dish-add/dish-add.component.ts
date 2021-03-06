import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {MatDialog} from "@angular/material/dialog";
import {MealAddComponent} from "../meal-add/meal-add.component";
import {MealService} from "../../service/meal.service";
import {Menu} from "../../model/menu";
import {FormArray} from "@angular/forms";

@Component({
  selector: 'app-dish-add',
  templateUrl: './dish-add.component.html',
  styleUrls: ['./dish-add.component.css']
})
export class DishAddComponent implements OnInit {

  constructor(
    private dialog: MatDialog,
    private mealSerivce: MealService
  ) { }

  @Input()
  dayId: string;
  @Input()
  dishType: string;
  @Input()
  menuItem: Menu;
  @Output()
  refreshItems = new EventEmitter<boolean>();

  ngOnInit(): void {
  }

  openDialog() {
    this.mealSerivce.initializeFormGroup();
    this.mealSerivce.form.get('foodType').patchValue(this.dishType);
    this.mealSerivce.form.get('dayMealId').patchValue(this.dayId);
    (<FormArray>this.mealSerivce.form.get('productList')).push(this.mealSerivce.addProductFormGroup());

    let dialogRef = this.dialog.open(MealAddComponent, {
      disableClose: true,
      autoFocus: true,
      width: "90%"
    });

    dialogRef.afterClosed().subscribe( result => {
      this.refreshItems.emit();
    });
  }
}
