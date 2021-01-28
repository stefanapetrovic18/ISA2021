import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Item } from 'src/app/model/business/item';
import { ItemService } from 'src/app/service/business/item.service';
import { ItemAddComponent } from '../item-add/item-add.component';
import { ItemDeleteComponent } from '../item-delete/item-delete.component';
import { ItemEditComponent } from '../item-edit/item-edit.component';
import { ItemViewComponent } from '../item-view/item-view.component';

@Component({
  selector: 'app-item-table-view',
  templateUrl: './item-table-view.component.html',
  styleUrls: ['./item-table-view.component.css']
})
export class ItemTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Item[];
  columns = ['x', 'y'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private itemService: ItemService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    if (this.data !== undefined && this.data.length > 0) {
      this.dataSource = new MatTableDataSource(this.data);
      console.log(this.dataSource);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    } else {
      window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
      this.router.navigateByUrl('');
    }
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
    this.dialog.open(ItemAddComponent);
  }

  view(input: Item) {
    this.dialog.open(ItemViewComponent, {
      data: input
    });
  }

  edit(input: Item) {
    this.dialog.open(ItemEditComponent, {
      data: input
    });
  }

  delete(input: Item) {
    this.dialog.open(ItemDeleteComponent, {
      data: input
    });
  }

}
