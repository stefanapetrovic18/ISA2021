import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Router } from '@angular/router';
import { WorkingHours } from 'src/app/model/business/working-hours';
import { WorkingHoursService } from 'src/app/service/business/working-hours.service';
import { WorkingHoursAddComponent } from '../working-hours-add/working-hours-add.component';
import { WorkingHoursDeleteComponent } from '../working-hours-delete/working-hours-delete.component';
import { WorkingHoursEditComponent } from '../working-hours-edit/working-hours-edit.component';
import { WorkingHoursViewComponent } from '../working-hours-view/working-hours-view.component';

@Component({
  selector: 'app-working-hours-table-view',
  templateUrl: './working-hours-table-view.component.html',
  styleUrls: ['./working-hours-table-view.component.css']
})
export class WorkingHoursTableViewComponent implements OnInit {

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  dataSource: MatTableDataSource<any>;
  @Input() data: WorkingHours[];
  columns = ['employeeID', 'dayOfWeek', 'shiftStart', 'shiftEnd'];
  actions = ['pharmacy', 'view', 'edit', 'delete'];
  displayedColumns = [...this.columns, ...this.actions];
  constructor(private workingHoursService: WorkingHoursService, private router: Router, private dialog: MatDialog) {}
  ngOnInit() {
    this.workingHoursService.findAll().subscribe(
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
    this.dialog.open(WorkingHoursAddComponent);
  }

  view(input: WorkingHours) {
    this.dialog.open(WorkingHoursViewComponent, {
      data: input
    });
  }

  edit(input: WorkingHours) {
    this.dialog.open(WorkingHoursEditComponent, {
      data: input
    });
  }

  delete(input: WorkingHours) {
    this.workingHoursService.delete(input.id).subscribe(
      data => {
        window.alert('Obrisano!');
      }, error => {
        window.alert('Nije obrisano!');
      }
    );
  }

}
