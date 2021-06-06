import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog} from '@angular/material/dialog';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import {ActivatedRoute, Router} from '@angular/router';
import {Dermatologist} from 'src/app/model/user/dermatologist';
import {DermatologistService} from 'src/app/service/user/dermatologist.service';
import {DermatologistAddComponent} from '../dermatologist-add/dermatologist-add.component';
import {DermatologistDeleteComponent} from '../dermatologist-delete/dermatologist-delete.component';
import {DermatologistEditComponent} from '../dermatologist-edit/dermatologist-edit.component';
import {DermatologistViewComponent} from '../dermatologist-view/dermatologist-view.component';
import {Consultation} from '../../../../model/business/consultation';
import {ExaminationAddComponent} from '../../../business/examination/examination-add/examination-add.component';
import { PatientService } from 'src/app/service/user/patient.service';
import { Patient } from 'src/app/model/user/patient';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { RatingAddComponent } from 'src/app/components/business/rating/rating-add/rating-add.component';

@Component({
  selector: 'app-dermatologist-table-view',
  templateUrl: './dermatologist-table-view.component.html',
  styleUrls: ['./dermatologist-table-view.component.css']
})
export class DermatologistTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  data: Dermatologist[];
  date: Date;
  patient: Patient;
  columns = ['username', 'forename', 'surname', 'rating'];
  actions = [];
  displayedColumns = [...this.columns, ...this.actions];

  constructor(private dermatologistService: DermatologistService, private router: Router, private dialog: MatDialog,
              private route: ActivatedRoute, private patientService: PatientService,
              private tokenStorageService: TokenStorageService) {
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe(
      params => {
        if (params.get('pharmacyID') !== null) {
          this.actions = ['rezervacija'];
          this.displayedColumns = [...this.columns, ...this.actions];
          const id = Number.parseInt(params.get('pharmacyID'));
          this.date = new Date(params.get('localDateTime'));
          this.dermatologistService.findAllByPharmaciesContaining(id).subscribe(
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
              window.alert('Podaci ne postoje!');
              // this.router.navigateByUrl('');
            }
          );
        } else {
          this.dermatologistService.findAll().subscribe(
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
              window.alert('Podaci ne postoje!');
              // this.router.navigateByUrl('');
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
          this.actions.push('rate');
          this.displayedColumns = [...this.columns, ...this.actions];
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
    const dialogRef = this.dialog.open(DermatologistAddComponent);
    dialogRef.afterClosed().subscribe(
      // result => {
      //   console.log('RESULT: ', result);
      //   if (result !== undefined) {
      //     this.dermatologistService.findAll().subscribe(
      //       data => {
      //         console.log('DATA: ', data);
      //       }
      //     )
      //   }
      // }
    );
    this.ngOnInit();
  }

  view(input: Dermatologist) {
    this.dialog.open(DermatologistViewComponent, {
      data: input
    });
  }

  edit(input: Dermatologist) {
    this.dialog.open(DermatologistEditComponent, {
      data: input
    });
  }

  delete(input: Dermatologist) {
    this.dermatologistService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }

  reserve(input: Dermatologist) {
    this.dialog.open(ExaminationAddComponent);
  }

  rate(input: Dermatologist) {
    this.dialog.open(RatingAddComponent, {
      data: {
        type: 'dermatologist',
        id: input.id,
        patient: this.patient
      }
    });
  }

}
