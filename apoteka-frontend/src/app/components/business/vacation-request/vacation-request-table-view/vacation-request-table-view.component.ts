import {Component, Inject, Input, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { VacationRequest } from 'src/app/model/business/vacation-request';
import { VacationRequestService } from 'src/app/service/business/vacation-request.service';
import { VacationRequestAddComponent } from '../vacation-request-add/vacation-request-add.component';
import {TokenStorageService} from '../../../../auth/token-storage.service';

@Component({
  selector: 'app-vacation-request-table-view',
  templateUrl: './vacation-request-table-view.component.html',
  styleUrls: ['./vacation-request-table-view.component.css']
})
export class VacationRequestTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  data: VacationRequest[];
  columns = [ 'employeeID', 'vacationStart', 'vacationEnd', 'accepted', 'rejected'];
  actions = [ 'pharmacy'];
  displayedColumns = [...this.columns, ...this.actions];
  admin = false;
  constructor(private vacationRequestService: VacationRequestService, private router: Router,
              private dialog: MatDialog, private token: TokenStorageService) {}
  ngOnInit() {
    if (this.token.getAuthorities().includes('ROLE_PHARMACY_ADMIN')) {
      this.admin = true;
      this.actions.push('accept');
      this.actions.push('reject');
      this.displayedColumns = [...this.columns, ...this.actions];
    }
    this.vacationRequestService.findAll().subscribe(
      data => {
        console.log(data);
        this.data = data;
        if (this.data !== undefined && this.data.length > 0) {
          this.dataSource = new MatTableDataSource(this.data);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        } else {
          window.alert('Podaci ne postoje!');
          // this.router.navigateByUrl('');
        }
      }, error => {
        window.alert('Podaci ne postoje!');
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
    this.dialog.open(VacationRequestAddComponent);
  }


  accept(input: VacationRequest) {
    this.vacationRequestService.accept(input).subscribe(
      data => {
        window.alert('Zahtev prihvaćen!');
      }, error => {
        window.alert('Zahtev nije prihvaćen!');
      }
    );
  }

  reject(input: VacationRequest) {
    this.vacationRequestService.reject(input).subscribe(
      data => {
        window.alert('Zahtev odbijen!');
      }, error => {
        window.alert('Zahtev nije odbijen!');
      }
    );
  }

}
