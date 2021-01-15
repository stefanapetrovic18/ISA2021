import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { AuthService } from 'src/app/auth/auth.service';
import { PasswordChangeRequest } from 'src/app/auth/password-change-request';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { LoginComponent } from '../login/login.component';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  private changed = false;
  private failed = false;
  request: PasswordChangeRequest = new PasswordChangeRequest();

  constructor(private token: TokenStorageService, private auth: AuthService) { }

  ngOnInit() {
  }
  change() {
    this.auth.changePassword(this.request).subscribe(data => {
      this.changed = true;
      this.failed = false;
    }, error => {
      console.log(error);
      window.alert('Greška!');
      this.failed = true;
    });
  }
  cancel(){
  }

}
