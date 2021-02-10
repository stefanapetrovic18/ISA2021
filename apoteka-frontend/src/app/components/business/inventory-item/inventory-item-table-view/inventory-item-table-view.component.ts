import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { InventoryItem } from 'src/app/model/business/inventory-item';
import { InventoryItemService } from 'src/app/service/business/inventory-item.service';
import { InventoryItemAddComponent } from '../inventory-item-add/inventory-item-add.component';
import { InventoryItemDeleteComponent } from '../inventory-item-delete/inventory-item-delete.component';
import { InventoryItemEditComponent } from '../inventory-item-edit/inventory-item-edit.component';
import { InventoryItemViewComponent } from '../inventory-item-view/inventory-item-view.component';

@Component({
  selector: 'app-inventory-item-table-view',
  templateUrl: './inventory-item-table-view.component.html',
  styleUrls: ['./inventory-item-table-view.component.css']
})
export class InventoryItemTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: InventoryItem[];
  columns = ['quantity'];
  actions = ['view', 'edit', 'delete', 'medicine'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private inventoryItemService: InventoryItemService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.inventoryItemService.findAll().subscribe(
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
    this.dialog.open(InventoryItemAddComponent);
  }

  view(input: InventoryItem) {
    this.dialog.open(InventoryItemViewComponent, {
      data: input
    });
  }

  edit(input: InventoryItem) {
    this.dialog.open(InventoryItemEditComponent, {
      data: input
    });
  }

  delete(input: InventoryItem) {
    this.dialog.open(InventoryItemDeleteComponent, {
      data: input
    });
  }

}
