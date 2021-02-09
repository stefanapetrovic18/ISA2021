import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
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
  columns = ['x', 'y'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private examinationService: ExaminationService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
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

}
