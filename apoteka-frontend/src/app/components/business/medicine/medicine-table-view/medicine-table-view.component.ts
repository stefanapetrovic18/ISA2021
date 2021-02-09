import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Medicine } from 'src/app/model/business/medicine';
import { MedicineService } from 'src/app/service/business/medicine.service';
import { MedicineAddComponent } from '../medicine-add/medicine-add.component';
import { MedicineDeleteComponent } from '../medicine-delete/medicine-delete.component';
import { MedicineEditComponent } from '../medicine-edit/medicine-edit.component';
import { MedicineViewComponent } from '../medicine-view/medicine-view.component';

@Component({
  selector: 'app-medicine-table-view',
  templateUrl: './medicine-table-view.component.html',
  styleUrls: ['./medicine-table-view.component.css']
})
export class MedicineTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Medicine[];
  columns = ['x', 'y'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private medicineService: MedicineService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.medicineService.findAll().subscribe(
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
    this.dialog.open(MedicineAddComponent);
  }

  view(input: Medicine) {
    this.dialog.open(MedicineViewComponent, {
      data: input
    });
  }

  edit(input: Medicine) {
    this.dialog.open(MedicineEditComponent, {
      data: input
    });
  }

  delete(input: Medicine) {
    this.dialog.open(MedicineDeleteComponent, {
      data: input
    });
  }

}
