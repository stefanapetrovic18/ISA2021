import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { PharmacyAddComponent } from '../pharmacy-add/pharmacy-add.component';
import { PharmacyDeleteComponent } from '../pharmacy-delete/pharmacy-delete.component';
import { PharmacyEditComponent } from '../pharmacy-edit/pharmacy-edit.component';
import { PharmacyViewComponent } from '../pharmacy-view/pharmacy-view.component';

@Component({
  selector: 'app-pharmacy-table-view',
  templateUrl: './pharmacy-table-view.component.html',
  styleUrls: ['./pharmacy-table-view.component.css']
})
export class PharmacyTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Pharmacy[];
  columns = ['x', 'y'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private pharmacyService: PharmacyService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.pharmacyService.data.subscribe(
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
    )
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
    this.dialog.open(PharmacyAddComponent);
  }

  view(input: Pharmacy) {
    this.dialog.open(PharmacyViewComponent, {
      data: input
    });
  }

  edit(input: Pharmacy) {
    this.dialog.open(PharmacyEditComponent, {
      data: input
    });
  }

  delete(input: Pharmacy) {
    this.dialog.open(PharmacyDeleteComponent, {
      data: input
    });
  }

}
