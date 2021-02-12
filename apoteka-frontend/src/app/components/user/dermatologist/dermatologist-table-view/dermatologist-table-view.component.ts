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
  columns = ['username', 'forename', 'surname', 'rating'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];

  constructor(private dermatologistService: DermatologistService, private router: Router, private dialog: MatDialog,
              private route: ActivatedRoute) {
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
    this.dialog.open(DermatologistDeleteComponent, {
      data: input
    });
  }
  reserve(input: Dermatologist) {
    this.dialog.open(ExaminationAddComponent);
  }

}
