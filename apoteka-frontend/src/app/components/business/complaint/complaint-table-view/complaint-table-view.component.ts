import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { ComplaintResponse } from 'src/app/dto/complaint-response';
import { Complaint } from 'src/app/model/business/complaint';
import { ComplaintService } from 'src/app/service/business/complaint.service';
import { ComplaintAddComponent } from '../complaint-add/complaint-add.component';
import { ComplaintAnswerComponent } from '../complaint-answer/complaint-answer.component';

@Component({
  selector: 'app-complaint-table-view',
  templateUrl: './complaint-table-view.component.html',
  styleUrls: ['./complaint-table-view.component.css']
})
export class ComplaintTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: Complaint[];
  columns = ['id', 'patient', 'resolved'];
  actions = ['delete', 'answer'];
  displayedColumns = [...this.columns, ...this.actions];
  expandedElement: string | null;
  constructor(private complaintService: ComplaintService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.complaintService.findAll().subscribe(
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
        window.alert('Podaci ne postoje!');
        // this.router.navigateByUrl('');
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
    this.dialog.open(ComplaintAddComponent);
    location.reload();
  }

  answer(input: Complaint) {
    this.dialog.open(ComplaintAnswerComponent, {data: input});
  }

  delete(input: Complaint) {
    this.complaintService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }

}
