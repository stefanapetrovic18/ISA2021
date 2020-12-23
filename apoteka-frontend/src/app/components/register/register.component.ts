import { Component, OnInit } from '@angular/core';
import { RegistrationRequest } from '../../auth/registration-request';
import { TokenStorageService } from '../../auth/token-storage.service';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/auth.service';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  request: RegistrationRequest = new RegistrationRequest();
  private signedUp = false;
  private signUpFailed = false;
  repeatPassword = '';

  constructor(private token: TokenStorageService, private auth: AuthService, private dialogRef: MatDialogRef<RegisterComponent>) { }

  ngOnInit() {
    if (this.token.getToken !== null) {
      // this.dialogRef.close();

    }
  }
  register() {
    if (this.repeatPassword === this.request.password) {
      this.auth.register(this.request).subscribe(data => {
        this.signUpFailed = false;
        this.signedUp = true;
        this.dialogRef.close();
      }, error => {
        this.signUpFailed = true;
        window.alert(error.message);
        location.reload();
      })
    } else {
      window.alert('Šifre se ne poklapaju!');
      // location.reload();
    }
  }
  cancel() {
    this.dialogRef.close();
  }

}
