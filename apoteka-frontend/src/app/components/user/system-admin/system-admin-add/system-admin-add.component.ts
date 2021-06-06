import {Component, OnInit} from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {AuthService} from 'src/app/auth/auth.service';
import {Pharmacy} from 'src/app/model/business/pharmacy';
import {SystemAdmin} from 'src/app/model/user/system-admin';
import {PharmacyService} from 'src/app/service/business/pharmacy.service';
import {SystemAdminService} from 'src/app/service/user/system-admin.service';

@Component({
  selector: 'app-system-admin-add',
  templateUrl: './system-admin-add.component.html',
  styleUrls: ['./system-admin-add.component.css']
})
export class SystemAdminAddComponent implements OnInit {

  data = new SystemAdmin();
  output = new SystemAdmin();
  users: SystemAdmin[];
  user: SystemAdmin;
  repeatPassword = '';

  constructor(private systemAdminService: SystemAdminService,
              private authService: AuthService, private dialogRef: MatDialogRef<SystemAdminAddComponent>) {
  }

  ngOnInit() {
  }

  add() {
    if (this.data.password === this.repeatPassword) {
      this.systemAdminService.create(this.data).subscribe(
        data => {
          window.alert('Uspešno kreiranje!');
          this.output = data;
          this.dialogRef.close();
        }, error => {
          window.alert('Neuspešno kreiranje ->' + error.message);
        }
      );
    }
  }

  close() {
    this.dialogRef.close();
  }

}
