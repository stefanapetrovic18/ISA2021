import { Component, OnInit, Inject } from '@angular/core';
import { TokenStorageService } from '../../auth/token-storage.service';
import { AuthService } from '../../auth/auth.service';
import { LoginRequest } from '../../auth/login-request';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  private loggedIn = false;
  private loginFailed = false;
  request: LoginRequest = new LoginRequest();

  constructor(@Inject(MAT_DIALOG_DATA) public data: any, private token: TokenStorageService, private auth: AuthService, private dialogRef: MatDialogRef<LoginComponent>) { }

  ngOnInit() {
    console.log('token: ' + this.token.getToken());
    if (this.token.getToken !== null) {
      this.loggedIn = true;
    }
  }
  login() {
    this.auth.attemptAuth(this.request).subscribe(data => {
      this.token.saveToken(data.token);
      this.token.saveUsername(data.username);
      this.token.saveAuthorities(data.authorities);
      this.loggedIn = true;
      this.loginFailed = false;
      this.dialogRef.close();
    }, error => {
      console.log(error);
      window.alert('Pogrešno korisničko ime i/ili lozinka!');
      this.loginFailed = true;
    });
  }
  cancel(){
    this.dialogRef.close();
  }

}
