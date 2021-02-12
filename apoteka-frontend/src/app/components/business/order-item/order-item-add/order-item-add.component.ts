import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {MedicineService} from '../../../../service/business/medicine.service';
import {Medicine} from '../../../../model/business/medicine';
import {OrderItemService} from '../../../../service/business/order-item.service';
import {OrderItem} from '../../../../model/business/order-item';

@Component({
  selector: 'app-order-item-add',
  templateUrl: './order-item-add.component.html',
  styleUrls: ['./order-item-add.component.css']
})
export class OrderItemAddComponent implements OnInit {
  data = new OrderItem();
  medicine: Medicine[];

  constructor(private dialogRef: MatDialogRef<OrderItemAddComponent>, private medicineService: MedicineService,
              private orderItemService: OrderItemService) { }

  ngOnInit() {
    this.medicineService.findAll().subscribe(
      data => {
        this.medicine = data;
      }
    );
  }
  add() {
    this.dialogRef.close({orderItem: this.data});
  }
  close() {
    this.dialogRef.close();
  }

}
