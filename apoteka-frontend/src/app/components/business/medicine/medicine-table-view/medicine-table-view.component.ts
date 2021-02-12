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
import {ReservationService} from '../../../../service/business/reservation.service';
import {Reservation} from '../../../../model/business/reservation';
import {ReservationAddComponent} from '../../reservation/reservation-add/reservation-add.component';

@Component({
  selector: 'app-medicine-table-view',
  templateUrl: './medicine-table-view.component.html',
  styleUrls: ['./medicine-table-view.component.css']
})
export class MedicineTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  medicine: Medicine[];
  dataSource: MatTableDataSource<any>;
  @Input() data: Medicine[];
  columns = ['name', 'manufacturer', 'code', 'type', 'form', 'prescriptionNecessary', 'sideEffects', 'recommendedDose'];
  actions = ['view', 'edit', 'delete', 'reserve'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private medicineService: MedicineService, private router: Router, private dialog: MatDialog,
              private reservationService: ReservationService) {}
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
          window.alert('Podaci ne postoje!');
          // this.router.navigateByUrl('');
        }
      }, error => {
        window.alert('Podaci ne postoje!');
        // this.router.navigateByUrl('');
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
    this.medicineService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }
  reserve(input: Medicine) {
    const r = new Reservation();
    const dialogRef = this.dialog.open(ReservationAddComponent);
    dialogRef.afterClosed().subscribe(
      result => {
        if (result.pharmacy !== undefined && result.pharmacy !== null && result.date !== undefined && result.date !== null) {
          console.log(result);
          r.pharmacy = result.pharmacy;
          r.medicine = input;
          r.collected = false;
          r.reservationDate = new Date();
          r.collectionDate = result.date;
          this.reservationService.reserve(r).subscribe(
            data => {
              window.alert('Rezervacija je uspela!');
            }, error => {
              window.alert('Rezervacija nije uspela!');
            }
          );
        }
      }
    );
  }

}
