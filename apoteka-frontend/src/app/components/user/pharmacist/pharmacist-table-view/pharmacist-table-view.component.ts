import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { PharmacistService } from 'src/app/service/user/pharmacist.service';
import { PharmacistAddComponent } from '../pharmacist-add/pharmacist-add.component';
import { PharmacistDeleteComponent } from '../pharmacist-delete/pharmacist-delete.component';
import { PharmacistEditComponent } from '../pharmacist-edit/pharmacist-edit.component';
import { PharmacistViewComponent } from '../pharmacist-view/pharmacist-view.component';

@Component({
  selector: 'app-pharmacist-table-view',
  templateUrl: './pharmacist-table-view.component.html',
  styleUrls: ['./pharmacist-table-view.component.css']
})
export class PharmacistTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  data: Pharmacist[];
  columns = ['username', 'forename', 'surname', 'rating'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private pharmacistService: PharmacistService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.pharmacistService.findAll().subscribe(
      data => {
        this.data = data;
        console.log(data);
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
    const dialogRef = this.dialog.open(PharmacistAddComponent);
    dialogRef.afterClosed().subscribe(
      // result => {
      //   console.log('RESULT: ', result);
      //   if (result !== undefined) {
      //     this.pharmacistService.data.subscribe(
      //       data => {
      //         console.log('DATA: ', data);
      //       }
      //     )
      //   }
      // }
    );
    this.ngOnInit();
  }

  view(input: Pharmacist) {
    this.dialog.open(PharmacistViewComponent, {
      data: input
    });
  }

  edit(input: Pharmacist) {
    this.dialog.open(PharmacistEditComponent, {
      data: input
    });
  }

  delete(input: Pharmacist) {
    this.dialog.open(PharmacistDeleteComponent, {
      data: input
    });
  }

}
