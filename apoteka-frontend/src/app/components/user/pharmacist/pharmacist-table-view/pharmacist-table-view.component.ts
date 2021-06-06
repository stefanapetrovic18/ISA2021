import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {ActivatedRoute, Router} from '@angular/router';
import {Pharmacist} from 'src/app/model/user/pharmacist';
import {PharmacistService} from 'src/app/service/user/pharmacist.service';
import {PharmacistAddComponent} from '../pharmacist-add/pharmacist-add.component';
import {PharmacistDeleteComponent} from '../pharmacist-delete/pharmacist-delete.component';
import {PharmacistEditComponent} from '../pharmacist-edit/pharmacist-edit.component';
import {PharmacistViewComponent} from '../pharmacist-view/pharmacist-view.component';
import {Consultation} from '../../../../model/business/consultation';
import { Patient } from 'src/app/model/user/patient';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { RatingAddComponent } from 'src/app/components/business/rating/rating-add/rating-add.component';
import { PatientService } from 'src/app/service/user/patient.service';

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
  date: Date;
  phadmin = false;
  patient: Patient;
  columns = ['username', 'forename', 'surname', 'rating'];
  actions = ['edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];

  constructor(private pharmacistService: PharmacistService, private router: Router, private dialog: MatDialog,
              private route: ActivatedRoute, private patientService: PatientService,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe(
      params => {
        if (params.get('pharmacyID') !== null && params.get('localDateTime') !== null) {
          this.actions = ['rezervacija'];
          this.displayedColumns = [...this.columns, ...this.actions];
          const id = Number.parseInt(params.get('pharmacyID'));
          this.date = new Date(params.get('localDateTime'));
          this.pharmacistService.findAllByPharmacistFreeAt(id, this.date).subscribe(
            data => {
              this.data = data;
              console.log(data);
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
        } else {
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
                window.alert('Podaci ne postoje!');
                // this.router.navigateByUrl('');
              }
            }, error => {
              window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
              this.router.navigateByUrl('');
            }
          );
        }
      }
    );
    if (this.tokenStorageService.getAuthorities().includes('ROLE_PATIENT')) {
      this.getPatient();
    } else if (this.tokenStorageService.getAuthorities().includes('ROLE_SYSTEM_ADMIN')) {
      this.actions.push('edit', 'delete');
      this.displayedColumns = [...this.columns, ...this.actions];
    } else if (this.tokenStorageService.getAuthorities().includes('ROLE_PHARMACY_ADMIN')) {
      this.phadmin = true;
      this.actions.push('fire');
      this.displayedColumns = [...this.columns, ...this.actions];
    }

  }

  getPatient() {
    this.patientService.findAll().subscribe((data) => {
      data.forEach((p) => {
        if (p.username === this.tokenStorageService.getUsername()) {
          this.patient = p;
          this.actions.push('rate');
          this.displayedColumns = [...this.columns, ...this.actions];
        }
      });
      // this.getPatient();
    });
  }

  fire(input: Pharmacist) {
    this.pharmacistService.fire(input).subscribe(
      data => {
        window.alert('Farmaceut je uspešno otpušten.');
      }, error => {
        window.alert('Farmaceut nije otpušten.');
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
      //     this.pharmacistService.findAll().subscribe(
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
    this.pharmacistService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }
  reserve(input: Pharmacist) {}

  rate(input: Pharmacist) {
    this.dialog.open(RatingAddComponent, {
      data: {
        type: 'pharmacist',
        id: input.id,
        patient: this.patient
      }
    });
  }

}
