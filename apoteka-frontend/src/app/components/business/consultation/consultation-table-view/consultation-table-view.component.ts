import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-consultation-table-view',
  templateUrl: './consultation-table-view.component.html',
  styleUrls: ['./consultation-table-view.component.css']
})
export class ConsultationTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  columns = ['x', 'y'];
  displayedColumns = ['x', 'y', 'view', 'edit', 'delete'];
  constructor() {}
  ngOnInit() {
    let data = [];
    data.push({'x': 1, 'y': 2}, {'x': 2, 'y': 3});
    this.dataSource = new MatTableDataSource(data);

    console.log(this.dataSource);
    this.dataSource.paginator = this.paginator;
    this.dataSource.sort = this.sort;
  }

  applyFilter(value: any) {
    this.dataSource.filter = value.trim().toLowerCase();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  filter() {

  }

  add() {

  }

  view(input: any) {

  }

  edit(input: any) {

  }

  delete(input: any) {

  }

}
