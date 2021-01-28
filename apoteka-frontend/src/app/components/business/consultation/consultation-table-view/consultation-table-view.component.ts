import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
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

  dataSource: MatTableDataSource<any>;
  @Input() data: Consultation[];
  columns = ['x', 'y'];
  actions = ['view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private consultationService: ConsultationService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    if (this.data !== undefined && this.data.length > 0) {
      this.dataSource = new MatTableDataSource(this.data);
      console.log(this.dataSource);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    } else {
      window.alert('Podaci ne postoje! Povratak na pocetnu stranu...');
      // this.router.navigateByUrl('');
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
    this.dialog.open(ConsultationDeleteComponent, {
      data: input
    });
  }

}
