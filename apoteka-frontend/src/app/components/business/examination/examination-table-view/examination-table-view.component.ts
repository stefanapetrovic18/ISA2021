import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Examination } from 'src/app/model/business/examination';
import { ExaminationService } from 'src/app/service/business/examination.service';
import { ExaminationAddComponent } from '../examination-add/examination-add.component';
import { ExaminationDeleteComponent } from '../examination-delete/examination-delete.component';
import { ExaminationEditComponent } from '../examination-edit/examination-edit.component';
import { ExaminationViewComponent } from '../examination-view/examination-view.component';

@Component({
  selector: 'app-examination-table-view',
  templateUrl: './examination-table-view.component.html',
  styleUrls: ['./examination-table-view.component.css']
})
export class ExaminationTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Examination[];
  columns = ['examinationDate', 'duration', 'price', 'dermatologist', 'pharmacy'];
  actions = ['view', 'edit', 'delete', 'reserve', 'cancel'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private examinationService: ExaminationService, private router: Router, private dialog: MatDialog,
              private route: ActivatedRoute) {}
  ngOnInit() {
    this.route.queryParamMap.subscribe(
      params => {
        console.log(params.get('pharmacyID'));
        if (params.get('reserved') !== null && params.get('reserved') === 'false') {
          this.examinationService.findAllFree().subscribe(
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
        } else if (params.get('reserved') !== null && params.get('reserved') === 'true') {
          this.examinationService.findAllReserved().subscribe(
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
        } else if (params.get('pharmacyID') !== null) {
          this.examinationService.findAllByPharmacy(Number.parseInt(params.get('pharmacyID'))).subscribe(
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
        } else if (params.get('patientID') !== null) {
          this.examinationService.findAllByPatient(Number.parseInt(params.get('patientID'))).subscribe(
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
        } else if (params.get('dermatologistID') !== null) {
          this.examinationService.findAllByDermatologist(Number.parseInt(params.get('dermatologistID'))).subscribe(
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
        } else {
          this.examinationService.findAll().subscribe(
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
    this.dialog.open(ExaminationAddComponent);
  }

  view(input: Examination) {
    this.dialog.open(ExaminationViewComponent, {
      data: input
    });
  }

  edit(input: Examination) {
    this.dialog.open(ExaminationEditComponent, {
      data: input
    });
  }

  delete(input: Examination) {
    this.dialog.open(ExaminationDeleteComponent, {
      data: input
    });
  }

  reserve(input: Examination) {
    this.examinationService.quickReserve(input).subscribe(
      data => {
        window.alert("Uspešna rezervacija! Molimo vas da proverite vaš email za potvrdu.");
      }, error => {
        window.alert("Neuspešna rezervacija!");
      }
    );
  }

  cancel(input: Examination) {
    this.examinationService.cancel(input).subscribe(
      data => {
        window.alert("Uspešno otkazivanje!");
      }, error => {
        window.alert("Neuspešno otkazivanje!");
      }
    )
  }

}
