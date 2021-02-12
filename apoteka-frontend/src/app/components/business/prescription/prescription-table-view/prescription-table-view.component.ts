import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Prescription } from 'src/app/model/business/prescription';
import { PrescriptionService } from 'src/app/service/business/prescription.service';
import { PrescriptionAddComponent } from '../prescription-add/prescription-add.component';
import { PrescriptionDeleteComponent } from '../prescription-delete/prescription-delete.component';
import { PrescriptionEditComponent } from '../prescription-edit/prescription-edit.component';
import { PrescriptionViewComponent } from '../prescription-view/prescription-view.component';

@Component({
  selector: 'app-prescription-table-view',
  templateUrl: './prescription-table-view.component.html',
  styleUrls: ['./prescription-table-view.component.css']
})
export class PrescriptionTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Prescription[];
  columns = ['x', 'y'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private prescriptionService: PrescriptionService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.prescriptionService.findAll().subscribe(
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
    this.dialog.open(PrescriptionAddComponent);
  }

  view(input: Prescription) {
    this.dialog.open(PrescriptionViewComponent, {
      data: input
    });
  }

  edit(input: Prescription) {
    this.dialog.open(PrescriptionEditComponent, {
      data: input
    });
  }

  delete(input: Prescription) {
    this.dialog.open(PrescriptionDeleteComponent, {
      data: input
    });
  }

}
