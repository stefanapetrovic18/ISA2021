import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/auth/auth.service';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { Consultation } from 'src/app/model/business/consultation';
import { ConsultationService } from 'src/app/service/business/consultation.service';
import { ConsultationAddComponent } from '../consultation-add/consultation-add.component';
import { ConsultationDeleteComponent } from '../consultation-delete/consultation-delete.component';
import { ConsultationEditComponent } from '../consultation-edit/consultation-edit.component';
import { ConsultationViewComponent } from '../consultation-view/consultation-view.component';

@Component({
  selector: 'app-consultation-table-view',
  templateUrl: './consultation-table-view.component.html',
  styleUrls: ['./consultation-table-view.component.css']
})
export class ConsultationTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  patient = false;
  dataSource: MatTableDataSource<any>;
  @Input() data: Consultation[];
  columns = ['consultationDate', 'duration', 'price'];
  actions = ['patient', 'pharmacy', 'pharmacist', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private consultationService: ConsultationService, private router: Router, private dialog: MatDialog,
    private token: TokenStorageService, private route: ActivatedRoute) {}
  ngOnInit() {
    if (this.token.getAuthorities().includes('ROLE_PATIENT')) {
      this.actions.push('cancel');
      this.displayedColumns = [...this.columns, ...this.actions];
    }
    let pharmacyID;
    this.route.queryParams.subscribe(params => {
      pharmacyID = params['pharmacyID'];
    });
    if (pharmacyID !== undefined && pharmacyID !== null) {
      this.consultationService.findAllByPharmacy(pharmacyID).subscribe(
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
      );
    } else {
      this.consultationService.findAll().subscribe(
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
      );
    }
    if (this.token.getAuthorities().includes('ROLE_PATIENT')) {
      this.patient = true;
    }
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
    this.dialog.open(ConsultationAddComponent);
  }

  view(input: Consultation) {
    this.dialog.open(ConsultationViewComponent, {
      data: input
    });
  }

  edit(input: Consultation) {
    this.dialog.open(ConsultationEditComponent, {
      data: input
    });
  }

  delete(input: Consultation) {
    this.consultationService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }
  cancel(input: Consultation) {
    this.consultationService.cancel(input).subscribe(
      data => {
        window.alert('Otkazano!');
      }, error => {
        window.alert('Nije otkazano!');
      }
    );
  }

}
