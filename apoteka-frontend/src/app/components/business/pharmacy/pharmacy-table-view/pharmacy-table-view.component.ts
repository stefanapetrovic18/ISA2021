import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import {ActivatedRoute, Router} from '@angular/router';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Patient } from 'src/app/model/user/patient';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { PatientService } from 'src/app/service/user/patient.service';
import { RatingAddComponent } from '../../rating/rating-add/rating-add.component';
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
  data: Pharmacy[];
  date: Date;
  patient: Patient;
  columns = ['name', 'address', 'rating'];
  actions = ['view', 'edit', 'delete', 'rate'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private pharmacyService: PharmacyService, private router: Router, private dialog: MatDialog,
              private route: ActivatedRoute, private patientService: PatientService,
              private tokenStorageService: TokenStorageService) {}
  ngOnInit() {
    this.route.queryParamMap.subscribe(
      params => {
        console.log(params);
        if (params.get('localDateTime') !== undefined && params.get('localDateTime')) {
          window.alert('STVARNO');
          this.actions = ['reserve consultation'];
          this.displayedColumns = [...this.columns, ...this.actions];
          this.date = new Date(params.get('localDateTime'));
          this.pharmacyService.findAllByPharmacistFreeAt(this.date).subscribe(
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
                // this.router.navigateByUrl('');
              }
            }, error => {
              window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
              // this.router.navigateByUrl('');
            }
          );
        } else if (params.get('sub') !== null && params.get('sub') === 'true') {
          this.pharmacyService.findSubs().subscribe(
            data => {
              this.data = data;
              if (this.data !== undefined && this.data.length > 0) {
                this.dataSource = new MatTableDataSource(this.data);
                console.log(this.dataSource);
                this.dataSource.paginator = this.paginator;
                this.dataSource.sort = this.sort;
              } else {
                window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
                // this.router.navigateByUrl('');
              }
            }, error => {
              window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
              // this.router.navigateByUrl('');
            }
          );
        } else {
          this.pharmacyService.findAll().subscribe(
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
                // this.router.navigateByUrl('');
              }
            }, error => {
              window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
              // this.router.navigateByUrl('');
            }
          );
        }
      }
    );
    if (this.tokenStorageService.getAuthorities().includes('ROLE_PATIENT')) {
      this.getPatient();
    }
  }

  applyFilter(value: any) {
    this.dataSource.filter = value.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  getPatient() {
    this.patientService.findAll().subscribe((data) => {
      data.forEach((p) => {
        if (p.username === this.tokenStorageService.getUsername()) {
          this.patient = p;
        }
      });
      // this.getPatient();
    });
  }

  filter() {
    // Kreiraj objekat za filtriranje i izbaci podatke iz tabele.
    // Objekat sadrzi:
    // - datum (gornja i donja granica)
    // - cena (gornja i donja granica)
    // - farmaceut (izbor)
  }

  add() {
    const dialogRef = this.dialog.open(PharmacyAddComponent);
    dialogRef.afterClosed().subscribe(
      // result => {
      //   console.log('RESULT: ', result);
      //   if (result !== undefined) {
      //     this.pharmacyService.findAll().subscribe(
      //       data => {
      //         console.log('DATA: ', data);
      //       }
      //     )
      //   }
      // }
    );
    this.ngOnInit();
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
    this.pharmacyService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }
  reserve(input: Pharmacy) {
    this.router.navigateByUrl('farmaceut?pharmacyID=' + input.id + '&localDateTime=' + this.date);
  }
  rate(input: Pharmacy) {
    this.dialog.open(RatingAddComponent, {
      data: {
        type: 'pharmacy',
        id: input.id,
        patient: this.patient
      }
    });
  }

}
