import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { OrderItem } from 'src/app/model/business/order-item';
import { OrderItemService } from 'src/app/service/business/order-item.service';
import { OrderItemAddComponent } from '../order-item-add/order-item-add.component';
import { OrderItemDeleteComponent } from '../order-item-delete/order-item-delete.component';
import { OrderItemEditComponent } from '../order-item-edit/order-item-edit.component';
import { OrderItemViewComponent } from '../order-item-view/order-item-view.component';

@Component({
  selector: 'app-order-item-table-view',
  templateUrl: './order-item-table-view.component.html',
  styleUrls: ['./order-item-table-view.component.css']
})
export class OrderItemTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: OrderItem[];
  columns = ['quantity'];
  actions = ['view', 'edit', 'delete', 'medicine'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private orderItemService: OrderItemService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.orderItemService.findAll().subscribe(
      data => {
        this.data = data;
        if (this.data !== undefined && this.data.length > 0) {
          this.dataSource = new MatTableDataSource(this.data);
          console.log(this.dataSource);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        } else {
          window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
          this.router.navigateByUrl('');
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
    this.dialog.open(OrderItemAddComponent);
  }

  view(input: OrderItem) {
    this.dialog.open(OrderItemViewComponent, {
      data: input
    });
  }

  edit(input: OrderItem) {
    this.dialog.open(OrderItemEditComponent, {
      data: input
    });
  }

  delete(input: OrderItem) {
    this.dialog.open(OrderItemDeleteComponent, {
      data: input
    });
  }

}
