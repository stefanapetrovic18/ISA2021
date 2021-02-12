import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Inventory } from 'src/app/model/business/inventory';
import { InventoryService } from 'src/app/service/business/inventory.service';
import { InventoryAddComponent } from '../inventory-add/inventory-add.component';
import { InventoryDeleteComponent } from '../inventory-delete/inventory-delete.component';
import { InventoryEditComponent } from '../inventory-edit/inventory-edit.component';
import { InventoryViewComponent } from '../inventory-view/inventory-view.component';

@Component({
  selector: 'app-inventory-table-view',
  templateUrl: './inventory-table-view.component.html',
  styleUrls: ['./inventory-table-view.component.css']
})
export class InventoryTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Inventory[];
  columns = ['expiryDate', 'y'];
  actions = ['view', 'edit', 'delete', 'medicine'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private inventoryService: InventoryService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.inventoryService.findAll().subscribe(
      data => {
        this.data = data;
        if (this.data !== undefined && this.data.length > 0) {
          this.dataSource = new MatTableDataSource(this.data);
          console.log(this.dataSource);
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
    this.dialog.open(InventoryAddComponent);
  }

  view(input: Inventory) {
    this.dialog.open(InventoryViewComponent, {
      data: input
    });
  }

  edit(input: Inventory) {
    this.dialog.open(InventoryEditComponent, {
      data: input
    });
  }

  delete(input: Inventory) {
    this.inventoryService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }
  medicine(input: Inventory) {
    this.router.navigateByUrl('lekovi?inventoryID=' + input.id);
  }

}
