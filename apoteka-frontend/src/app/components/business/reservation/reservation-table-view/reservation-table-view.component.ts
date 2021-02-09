import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { Reservation } from 'src/app/model/business/reservation';
import { ReservationService } from 'src/app/service/business/reservation.service';
import { ReservationAddComponent } from '../reservation-add/reservation-add.component';
import { ReservationDeleteComponent } from '../reservation-delete/reservation-delete.component';
import { ReservationEditComponent } from '../reservation-edit/reservation-edit.component';
import { ReservationViewComponent } from '../reservation-view/reservation-view.component';

@Component({
  selector: 'app-reservation-table-view',
  templateUrl: './reservation-table-view.component.html',
  styleUrls: ['./reservation-table-view.component.css']
})
export class ReservationTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Reservation[];
  columns = ['x', 'y'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private reservationService: ReservationService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.reservationService.findAll().subscribe(
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
    this.dialog.open(ReservationAddComponent);
  }

  view(input: Reservation) {
    this.dialog.open(ReservationViewComponent, {
      data: input
    });
  }

  edit(input: Reservation) {
    this.dialog.open(ReservationEditComponent, {
      data: input
    });
  }

  delete(input: Reservation) {
    this.dialog.open(ReservationDeleteComponent, {
      data: input
    });
  }

}
