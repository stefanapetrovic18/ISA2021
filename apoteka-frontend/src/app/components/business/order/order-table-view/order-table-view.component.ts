import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Order } from 'src/app/model/business/order';
import { OrderService } from 'src/app/service/business/order.service';
import { OrderAddComponent } from '../order-add/order-add.component';
import { OrderDeleteComponent } from '../order-delete/order-delete.component';
import { OrderEditComponent } from '../order-edit/order-edit.component';
import { OrderViewComponent } from '../order-view/order-view.component';
import {OrderItemTableViewComponent} from '../../order-item/order-item-table-view/order-item-table-view.component';

@Component({
  selector: 'app-order-table-view',
  templateUrl: './order-table-view.component.html',
  styleUrls: ['./order-table-view.component.css']
})
export class OrderTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Order[];
  columns = ['expiryDate'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private orderService: OrderService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.orderService.findAll().subscribe(
      data => {
        this.data = data;
        if (this.data !== undefined && this.data.length > 0) {
          this.dataSource = new MatTableDataSource(this.data);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        } else {
          window.alert('Podaci ne postoje!');
        // this.router.navigateByUrl('');
        }
      }, error => {
        window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
        this.router.navigateByUrl('');
      }
    );
  }

  applyFilter(value: any) {
    this.dataSource.filter = value.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  filter() {
    // Kreiraj objekat za filtriranje i izbaci podatke iz tabele.
    // Objekat sadrzi:
    // - datum (gornja i donja granica)
    // - cena (gornja i donja granica)
    // - farmaceut (izbor)
  }

  add() {
    this.dialog.open(OrderAddComponent);
  }

  view(input: Order) {
    this.dialog.open(OrderItemTableViewComponent, {
      data: input.items
    });
  }

  edit(input: Order) {
    this.dialog.open(OrderEditComponent, {
      data: input
    });
  }

  delete(input: Order) {
    this.orderService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }
  medicine(input: Order) {
    this.router.navigateByUrl('lekovi?orderID=' + input.id);
  }

}
