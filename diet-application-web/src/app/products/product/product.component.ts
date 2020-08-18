import { Component, OnInit } from '@angular/core';
import {ProductService} from "../../service/product.service";
import {MatDialogRef} from "@angular/material/dialog";
import {NotificationService} from "../../service/notification.service";


@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  categories: any;
  subcategories: any = [];

  constructor(
    private service: ProductService,
    private notificationService: NotificationService,
    public dialogRef: MatDialogRef<ProductComponent>
  ) { }

  ngOnInit(): void {
    this.getCategories();
  }

  getCategories() {
    let response = this.service.getCategories();
    response.subscribe(data => {
      this.categories = data;
      for (const key of Object.values(data))
        this.subcategories = this.subcategories.concat(key.subcategories);
    });
  }

  onClear() {
    this.service.form.reset();
    this.service.initializeFormGroup();
  }

  onSubmit() {
    if (this.service.form.valid) {
      if (!this.service.form.get('id').value) {
        this.service.insertProduct(this.service.form.value).subscribe();
        this.notificationService.success(":: Product created successfully! ::");
      } else {
        this.service.updateProduct(this.service.form.value).subscribe();
        this.notificationService.success(":: Product updated successfully! ::");
      }
      this.onClose();
    }
  }

  onClose() {
    this.onClear();
    this.dialogRef.close();
  }
}
