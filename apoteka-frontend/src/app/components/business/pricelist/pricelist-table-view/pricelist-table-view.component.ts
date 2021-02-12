import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Pricelist } from 'src/app/model/business/pricelist';
import { PricelistService } from 'src/app/service/business/pricelist.service';
import { PricelistAddComponent } from '../pricelist-add/pricelist-add.component';
import { PricelistDeleteComponent } from '../pricelist-delete/pricelist-delete.component';
import { PricelistEditComponent } from '../pricelist-edit/pricelist-edit.component';
import { PricelistViewComponent } from '../pricelist-view/pricelist-view.component';

@Component({
  selector: 'app-pricelist-table-view',
  templateUrl: './pricelist-table-view.component.html',
  styleUrls: ['./pricelist-table-view.component.css']
})
export class PricelistTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Pricelist[];
  columns = ['examinationPrice', 'consultationPrice', 'validFrom', 'validUntil'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private pricelistService: PricelistService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.pricelistService.findAll().subscribe(
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
    this.dialog.open(PricelistAddComponent);
  }

  view(input: Pricelist) {
    this.dialog.open(PricelistViewComponent, {
      data: input
    });
  }

  edit(input: Pricelist) {
    this.dialog.open(PricelistEditComponent, {
      data: input
    });
  }

  delete(input: Pricelist) {
    this.dialog.open(PricelistDeleteComponent, {
      data: input
    });
  }

}
