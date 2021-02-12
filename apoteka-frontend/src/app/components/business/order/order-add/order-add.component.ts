import { Component, OnInit } from '@angular/core';
import {OrderService} from '../../../../service/business/order.service';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {Order} from '../../../../model/business/order';
import {OrderItemAddComponent} from '../../order-item/order-item-add/order-item-add.component';

@Component({
  selector: 'app-order-add',
  templateUrl: './order-add.component.html',
  styleUrls: ['./order-add.component.css']
})
export class OrderAddComponent implements OnInit {
  data = new Order();

  constructor(private orderService: OrderService, private dialog: MatDialog, private router: Router,
              private dialogRef: MatDialogRef<OrderAddComponent>) { }

  ngOnInit() {
    this.data.items = [];
  }
  add() {
    this.orderService.create(this.data).subscribe(
      data => {
        window.alert('Narudžbina potvrđena!');
      }, error => {
        window.alert('Narudžbina nije potvrđena!');
      }
    );
  }
  close() {
    this.dialogRef.close();
  }
  addItem() {
    const dialogRef = this.dialog.open(OrderItemAddComponent);
    dialogRef.afterClosed().subscribe(
      result => {
        this.data.items.push(result.orderItem);
      }
    );
  }

}
